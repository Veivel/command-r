package com.github.veivel.commandr.core.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;

public final class MinecraftScreen {

    public static Screen getCurrent() {
        return Minecraft.getInstance().screen;
    }

    public static boolean isCurrentChatScreen() {
        return (Minecraft.getInstance().screen instanceof ChatScreen);
    }
}
