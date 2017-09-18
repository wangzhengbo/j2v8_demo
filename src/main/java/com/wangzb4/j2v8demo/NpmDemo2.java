package com.wangzb4.j2v8demo;

import com.eclipsesource.v8.V8Object;

public class NpmDemo2 {
    public static void main(String[] args) {
        final V8Object npm = NodeJSUtils.require("npm.js");

        while (NodeJSUtils.isRunning()) {
            NodeJSUtils.handleMessage();
        }

        npm.release();
        NodeJSUtils.release();
    }
}
