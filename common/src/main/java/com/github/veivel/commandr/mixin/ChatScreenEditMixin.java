package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.gui.screens.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenEditMixin {
  
  @Inject(at = @At("HEAD"), method = "onEdited")
  public void onEdited(final String value, CallbackInfo ci) {
    MixinRelay.setChatScreenQuery(value);
  }
}