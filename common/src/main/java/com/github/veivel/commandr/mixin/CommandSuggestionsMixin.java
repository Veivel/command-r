package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.core.MixinRelay;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;

import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {
  
  @Inject(at = @At("HEAD"), method = "updateCommandInfo", cancellable = true)
  public void updateCommandInfo(CallbackInfo ci) {
    Commandr.logger.info("Running updateCommandInfo");
    if (MixinRelay.inSearchMode() && ci.isCancellable()) {
      Commandr.logger.info("Cancelling!");
      ci.cancel();
    }
  }

  @Inject(at = @At("HEAD"), method = "updateUsageInfo", cancellable = true)
  private void updateUsageInfo(ParseResults<ClientSuggestionProvider> currentParse, Suggestions suggestions, CallbackInfo ci) {
    Commandr.logger.info("Running updateUsageInfo");
    if (MixinRelay.inSearchMode() && ci.isCancellable()) {
      Commandr.logger.info("Cancelling!");
      ci.cancel();
    }
  }

  // The intercepted method actually accepts a `Boolean allowSuggestions` 
  // parameter but the mixin will NOT allow it, I don't know why.
  @Inject(at = @At("HEAD"), method = "setAllowSuggestions", cancellable = true)
  public void setAllowSuggestions(CallbackInfo ci) {
    Commandr.logger.info("Running setAllowSuggestions");

    if (MixinRelay.inSearchMode() && ci.isCancellable()) {
      Commandr.logger.info("Cancelling!");
      ci.cancel();
    }
  }

  @Inject(at = @At("HEAD"), method = "hide")
  public void hide(CallbackInfo ci) {
    Commandr.logger.info("Running hide");
  }
}
