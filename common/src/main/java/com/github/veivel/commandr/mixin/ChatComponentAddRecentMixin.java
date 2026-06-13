package com.github.veivel.commandr.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.gui.components.ChatComponent;

@Mixin(ChatComponent.class)
public class ChatComponentAddRecentMixin {
  
  // TODO: make sure ChatComponent has no other entrypoints
  @Inject(at = @At("HEAD"), method = "addRecentChat")
  public void addRecentChat(final String message, CallbackInfo ci) {
    MixinRelay.addToHistory(message);
  }
}