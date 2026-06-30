package com.github.veivel.commandr.mixin;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.core.MixinRelay;
import net.minecraft.client.gui.components.CommandSuggestions.SuggestionsList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SuggestionsList.class)
public class SuggestionsListMixin {

    @Inject(at = @At("HEAD"), method = "useSuggestion")
    public void useSuggestion(CallbackInfo ci) {
        Commandr.logger.debug("useSuggestion called!");
        MixinRelay.onUseSuggestion();
    }
}
