package com.wangzb4.j2v8demo;

import java.io.IOException;

public class V8Demo {
    public static void main(String[] args) throws IOException, InterruptedException {
        NodeJSUtils.executeVoidScriptFile("polyfill.js");
        NodeJSUtils.executeVoidScriptFile("init.js");
        NodeJSUtils.executeVoidScriptFile("log.js");
        NodeJSUtils.executeVoidScriptFile("nextTick.js");
        NodeJSUtils.executeVoidScriptFile("setTimeout.js");
        NodeJSUtils.executeVoidScriptFile("class.js");
        NodeJSUtils.executeVoidScriptFile("es6feature.js");
        NodeJSUtils.executeVoidScriptFile("arrow-function.js");
        NodeJSUtils.executeVoidScriptFile("promise.js");
        while (NodeJSUtils.isRunning()) {
            // https://stackoverflow.com/questions/38593820/working-with-promises-in-j2v8
            NodeJSUtils.handleMessage();
        }
        
        System.out.println("------------------------------");
        V8Utils.executeVoidScriptFile("polyfill.js");
        V8Utils.executeVoidScriptFile("init.js");
        V8Utils.executeVoidScriptFile("log.js");
//        V8Utils.executeVoidScriptFile("nextTick.js");
//        V8Utils.executeVoidScriptFile("setTimeout.js");
        V8Utils.executeVoidScriptFile("class.js");
        V8Utils.executeVoidScriptFile("es6feature.js");
        V8Utils.executeVoidScriptFile("arrow-function.js");
        V8Utils.executeVoidScriptFile("promise.js");

        while (NodeJSUtils.isRunning()) {
            // https://stackoverflow.com/questions/38593820/working-with-promises-in-j2v8
            NodeJSUtils.handleMessage();
        }
    }
}
