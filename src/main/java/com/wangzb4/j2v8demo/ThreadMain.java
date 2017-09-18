package com.wangzb4.j2v8demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8ObjectUtils;

/**
 * @see https://github.com/irbull/j2v8_examples/blob/master/ThreadedMergeSort/src/com/ianbull/j2v8_examples/threadedmergesort/ThreadMain.java
 * @author wangzb4
 *
 */
public class ThreadMain {
    private List<List<Object>> mergeSortResults = new ArrayList<>();

    public class Sort implements JavaCallback {
        List<Object> result = null;

        public Object invoke(final V8Object receiver, final V8Array parameters) {
            final List<Object> data = V8ObjectUtils.toList(parameters);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    V8 runtime = V8.createV8Runtime();
                    runtime.registerJavaMethod(new Sort(), "_sort");
                    try {
                        runtime.executeVoidScript(FileUtil.getResourceFileContent("sortAlgorithm.js"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    V8Array parameters = V8ObjectUtils.toV8Array(runtime, data);
                    V8Array _result = runtime.executeArrayFunction("sort", parameters);
                    result = V8ObjectUtils.toList(_result);
                    _result.release();
                    parameters.release();
                    runtime.release();
                }
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return V8ObjectUtils.toV8Array(parameters.getRuntime(), result);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadMain().testMultiV8Threads();
    }

    public void testMultiV8Threads() throws InterruptedException {
        int totalThreads = 4;
        final List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < totalThreads; i++) {
            Thread t = new Thread(new Runnable() {
                public void run() {
                    V8 v8 = V8.createV8Runtime();
                    v8.registerJavaMethod(new Sort(), "_sort");
                    try {
                        v8.executeVoidScript(FileUtil.getResourceFileContent("sortAlgorithm.js"));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    V8Array data = new V8Array(v8);
                    int max = 100;
                    for (int i = 0; i < max; i++) {
                        data.push(max - i);
                    }
                    V8Array parameters = new V8Array(v8).push(data);
                    V8Array result = v8.executeArrayFunction("sort", parameters);
                    synchronized (threads) {
                        mergeSortResults.add(V8ObjectUtils.toList(result));
                    }
                    result.release();
                    parameters.release();
                    data.release();
                    v8.release();
                }
            });
            threads.add(t);
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        for (int i = 0; i < totalThreads; i++) {
            List<Object> result = mergeSortResults.get(i);
            for (int j = 0; j < result.size(); j++) {
                System.out.print(result.get(j) + " ");
            }
            System.out.println();
        }
    }
}
