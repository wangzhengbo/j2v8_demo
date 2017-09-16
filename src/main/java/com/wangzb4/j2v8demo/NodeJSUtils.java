package com.wangzb4.j2v8demo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import com.eclipsesource.v8.NodeJS;

public class NodeJSUtils {
    private static final NodeJS nodeJS;

    private NodeJSUtils() {
    }

    static {
        nodeJS = NodeJS.createNodeJS();
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

    public static void exec(String fileName) {
        File file = new File(V8Utils.class.getClassLoader().getResource(fileName).getPath());
        nodeJS.exec(file);
    }

    public static void executeVoidScriptFile(String fileName) throws IOException {
        nodeJS.getRuntime().executeVoidScript(
                IOUtils.toString(V8Utils.class.getClassLoader().getResourceAsStream(fileName), "UTF-8"));
    }
}
