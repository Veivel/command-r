package com.github.veivel.commandr.mixin;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.core.MixinRelay;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.suggestion.Suggestions;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandSuggestions.class)
public class CommandSuggestionsMixin {

    private void checkAndCancel(CallbackInfo ci) {
        if (MixinRelay.inSearchMode() && ci.isCancellable()) {
            Commandr.logger.debug("Cancelling!");
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "updateCommandInfo", cancellable = true)
    public void updateCommandInfo(CallbackInfo ci) {
        Commandr.logger.debug("Running updateCommandInfo");
        checkAndCancel(ci);
    }

    @Inject(at = @At("HEAD"), method = "updateUsageInfo", cancellable = true)
    private void updateUsageInfo(
        ParseResults<ClientSuggestionProvider> currentParse,
        Suggestions suggestions,
        CallbackInfo ci
    ) {
        Commandr.logger.debug("Running updateUsageInfo");
        checkAndCancel(ci);
    }

    // The intercepted method actually accepts a `Boolean allowSuggestions`
    // parameter but the mixin will NOT allow it, I don't know why.
    @Inject(
        at = @At("HEAD"),
        method = "setAllowSuggestions",
        cancellable = true
    )
    public void setAllowSuggestions(CallbackInfo ci) {
        Commandr.logger.debug("Running setAllowSuggestions");
        checkAndCancel(ci);
    }

    // @Inject(at = @At("HEAD"), method = "hide")
    // public void hide(CallbackInfo ci) {
    //   Commandr.logger.debug("Running hide");
    // }
}
