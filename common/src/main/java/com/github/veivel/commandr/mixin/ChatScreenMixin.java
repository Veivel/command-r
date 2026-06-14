package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.gui.screens.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
  
  @Inject(at = @At("HEAD"), method = "init")
  public void init(CallbackInfo ci) {
    MixinRelay.setChatScreenStatus(true);
  }

  @Inject(at = @At("HEAD"), method = "onEdited")
  public void onEdited(final String value, CallbackInfo ci) {
    MixinRelay.setChatScreenQuery(value);
  }

  @Inject(at = @At("HEAD"), method = "removed")
  public void removed(CallbackInfo ci) {
    MixinRelay.setChatScreenStatus(false);
  }

  // TODO: inject to ESC in search mode ("exit search mode")
}
