package com.wangzb4.j2v8demo;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;

/**
 * @see https://eclipsesource.com/blogs/2016/07/20/running-node-js-on-the-jvm/
 * @author wangzb4
 *
 */
public class HttpDemo {
    public static void main(String[] args) {
        JavaCallback callback = (V8Object receiver, V8Array parameters) -> "Hello, Java World!";
        NodeJSUtils.getRuntime().registerJavaMethod(callback, "someJavaMethod");
        NodeJSUtils.exec("http.js");

        while (NodeJSUtils.isRunning()) {
            NodeJSUtils.handleMessage();
        }

        NodeJSUtils.release();
    }
}
