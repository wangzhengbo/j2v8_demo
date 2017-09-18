package com.wangzb4.j2v8demo;

import java.io.IOException;
import java.lang.reflect.Method;

import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

public final class V8Utils {
    private static final V8 v8;

    private V8Utils() {
    }

    static {
        v8 = V8.createV8Runtime();
        registerConsole(v8);
    }

    public static void release() {
        v8.release();
    }

    public static void release(Object object) {
        if (object instanceof Releasable) {
            ((Releasable) object).release();
        }
    }

    public static String getV8Version() {
        return V8.getV8Version();
    }

    public static void executeVoidScriptFile(String fileName) throws IOException {
        v8.executeVoidScript(FileUtil.getResourceFileContent(fileName));
    }

    protected static void registerConsole(V8 v8) {
        Console console = new Console();
        V8Object v8Console = new V8Object(v8);
        v8.add("console", v8Console);
        v8Console.registerJavaMethod(console, "log", "log", new Class<?>[] { Object[].class });
        v8Console.registerJavaMethod(console, "error", "error", new Class<?>[] { Object[].class });
        v8Console.release();

    }

    public static void main(String[] args) {
        for (Method m : V8Utils.class.getDeclaredMethods()) {
            System.out.println(m.getName());
            if ("test".equals(m.getName())) {
                System.out.println(m.getParameters()[0].getType());
            }
        }
    }

    private static class Console {
        public void log(final Object... messages) {
            System.out.print("[INFO]");
            if (messages != null) {
                for (Object message : messages) {
                    System.out.print(" " + message);
                }
            }
            System.out.println();
        }

        public void error(final Object... messages) {
            System.err.print("[ERROR]");
            if (messages != null) {
                for (Object message : messages) {
                    System.err.print(" " + message);
                }
            }
            System.err.println();
        }
    }
}
