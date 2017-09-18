package com.wangzb4.j2v8demo;

import java.io.File;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;

public class NpmDemo {
    private static void release(Object object) {
        if (object == null) {
            return;
        }

        if (object instanceof Releasable) {
            ((Releasable) object).release();
        }
    }

    private static void executeJSFunction(V8Object object, final String name, final Object... parameters) {
        Object result = object.executeJSFunction(name, parameters);
        release(result);
    }

    public static void main(String[] args) {
        final NodeJS nodeJS = NodeJS.createNodeJS();
        final V8Object jimp = nodeJS.require(new File("./node_modules/jimp"));

        V8Function callback = new V8Function(nodeJS.getRuntime(), new JavaCallback() {
            public Object invoke(V8Object receiver, V8Array parameters) {
                final V8Object image = parameters.getObject(1);
                executeJSFunction(image, "posterize", 7);
                executeJSFunction(image, "greyscale");
                executeJSFunction(image, "write", "lenna_dest.jpg");
                image.release();
                parameters.release();
                receiver.release();

                return null;
            }
        });
        executeJSFunction(jimp, "read", FileUtil.getResourceFile("lenna.jpg").getAbsolutePath(), callback);

        while (nodeJS.isRunning()) {
            nodeJS.handleMessage();
        }

        callback.release();
        jimp.release();
        nodeJS.release();
    }
}
