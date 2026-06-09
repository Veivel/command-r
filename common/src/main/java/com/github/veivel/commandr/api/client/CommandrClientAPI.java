package com.github.veivel.commandr.api.client;

import java.lang.reflect.InvocationTargetException;

public class CommandrClientAPI {

    private static final InternalClientMethods __internalMethods;

    static {
        try {
            __internalMethods = (InternalClientMethods) Class.forName("com.github.veivel.commandr.client.InternalClientMethodsImpl").getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
