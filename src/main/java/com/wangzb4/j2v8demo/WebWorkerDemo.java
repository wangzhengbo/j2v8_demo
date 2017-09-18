package com.wangzb4.j2v8demo;

import java.io.IOException;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8Executor;

/**
 * @see https://github.com/irbull/j2v8_examples/blob/master/ThreadedMergeSort/src/com/ianbull/j2v8_examples/webworker/WebWorker.java
 * @author wangzb4
 *
 */
public class WebWorkerDemo {
    public void start(V8Object worker, String... s) {
        String script = (String) s[0];
        V8Executor executor = new V8Executor(script, true, "messageHandler") {
            @Override
            protected void setup(V8 runtime) {
                configureWorker(runtime);
            }
        };
        worker.getRuntime().registerV8Executor(worker, executor);
        executor.start();
    }

    public void terminate(V8Object worker, Object... s) {
        V8Executor executor = worker.getRuntime().removeExecutor(worker);
        if (executor != null) {
            executor.shutdown();
        }
    }

    public void postMessage(V8Object worker, String... s) {
        V8Executor executor = worker.getRuntime().getExecutor(worker);
        if (executor != null) {
            executor.postMessage(s);
        }
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void start() throws InterruptedException {
        V8Executor mainExecutor;
        try {
            mainExecutor = new V8Executor(FileUtil.getResourceFileContent("webWorker.js")) {
                @Override
                protected void setup(V8 runtime) {
                    configureWorker(runtime);
                }
            };
            mainExecutor.start();
            mainExecutor.join();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void configureWorker(V8 runtime) {
        runtime.registerJavaMethod(this, "start", "Worker", new Class<?>[] { V8Object.class, String[].class }, true);

        V8Object worker = runtime.getObject("Worker");
        V8Object prototype = runtime.executeObjectScript("Worker.prototype");
        prototype.registerJavaMethod(this, "terminate", "terminate", new Class<?>[] { V8Object.class, Object[].class },
                true);
        prototype.registerJavaMethod(this, "postMessage", "postMessage",
                new Class<?>[] { V8Object.class, String[].class }, true);
        worker.setPrototype(prototype);
        worker.release();
        prototype.release();

        runtime.registerJavaMethod(WebWorkerDemo.this, "print", "print", new Class<?>[] { String.class });
    }

    public static void main(String[] args) throws InterruptedException {
        new WebWorkerDemo().start();
    }
}
