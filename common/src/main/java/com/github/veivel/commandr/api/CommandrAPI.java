package com.github.veivel.commandr.api;

import java.lang.reflect.InvocationTargetException;

public class CommandrAPI {

    public static final String MOD_ID = "commandr";

    private static final InternalMethods __internalMethods;

    static {
        try {
            __internalMethods = (InternalMethods) Class.forName(
                "com.github.veivel.commandr.InternalMethodsImpl"
            )
                .getConstructor()
                .newInstance();
        } catch (
            InstantiationException
            | IllegalAccessException
            | InvocationTargetException
            | NoSuchMethodException
            | ClassNotFoundException e
        ) {
            throw new RuntimeException(e);
        }
    }
}
