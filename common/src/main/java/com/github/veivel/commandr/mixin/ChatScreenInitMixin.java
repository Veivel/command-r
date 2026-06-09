package com.github.veivel.commandr.mixin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screens.ChatScreen;

@Mixin(ChatScreen.class)
public class ChatScreenInitMixin {
  
  @Inject(at = @At("HEAD"), method = "init")
  public void init(CallbackInfo ci) {
    Logger logger = LogManager.getLogger("command-r");
    logger.info("awooga!");
  }
}
