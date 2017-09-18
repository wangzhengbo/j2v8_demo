package com.wangzb4.j2v8demo;

import java.io.File;
import java.io.IOException;

import com.eclipsesource.v8.NodeJS;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;

public class NodeJSUtils {
    private static final NodeJS nodeJS;
    private static final V8Function transformFunc;

    private NodeJSUtils() {
    }

    static {
        nodeJS = NodeJS.createNodeJS();
        transformFunc = (V8Function) require("babel/transform.js");
    }

    public static V8 getRuntime() {
        return nodeJS.getRuntime();
    }

    public static void release() {
        transformFunc.release();
        nodeJS.release();
    }

    public static void release(Object object) {
        V8Utils.release(object);
    }

    public static String getV8Version() {
        return V8.getV8Version();
    }

    public static V8Object require(String resourceFileName) {
        return require(FileUtil.getResourceFile(resourceFileName));
    }

    public static void require(String resourceFileName, boolean autoRelease) {
        V8Utils.release(require(FileUtil.getResourceFile(resourceFileName)));
    }

    public static V8Object require(File file) {
        return nodeJS.require(file);
    }

    public static void requireAndTransform(String resourceFileName) throws IOException {
        V8Array parameters = new V8Array(getRuntime());
        File transformedScriptFile = null;
        try {
            parameters.push(FileUtil.getResourceFileContent(resourceFileName));
            Object transformedScript = transformFunc.call(null, parameters);
            transformedScriptFile = FileUtil.createTempFile(String.valueOf(transformedScript));
            release(transformedScript);

            require(transformedScriptFile, true);
        } finally {
            parameters.release();
            if (transformedScriptFile != null) {
                transformedScriptFile.delete();
            }
        }
    }

    public static void require(File file, boolean autoRelease) {
        V8Utils.release(nodeJS.require(file));
    }

    /**
     * Returns true if there are more messages to process, false otherwise.
     *
     * @return True if there are more messages to process, false otherwise.
     */
    public static boolean isRunning() {
        return nodeJS.isRunning();
    }

    /**
     * Handles the next message in the message loop. Returns True if there are
     * more messages to handle, false otherwise.
     *
     * @return True if there are more messages to handle, false otherwise.
     */
    public static boolean handleMessage() {
        return nodeJS.handleMessage();
    }

    public static void exec(String resourceFileName) {
        exec(FileUtil.getResourceFile(resourceFileName));
    }

    public static void exec(File file) {
        nodeJS.exec(file);
    }

    public static void executeVoidScriptFile(String resourceFileName) throws IOException {
        nodeJS.getRuntime().executeVoidScript(FileUtil.getResourceFileContent(resourceFileName));
    }
}
