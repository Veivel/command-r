package com.github.veivel.commandr.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {
  
  @Inject(at = @At("HEAD"), method = "addRecentChat")
  public void addRecentChat(final String message, CallbackInfo ci) {
    MixinRelay.addToHistory(message);
  }

  // Inject class constructor
  @Inject(at = @At("HEAD"), method = "<init>")
  private static void ChatComponent(final Minecraft minecraft, CallbackInfo ci) {
    List<String> list = new ArrayList<String>(minecraft.commandHistory().history());
    MixinRelay.addAllToHistory(list);
  } 
}