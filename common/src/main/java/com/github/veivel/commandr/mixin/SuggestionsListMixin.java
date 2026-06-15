package com.github.veivel.commandr.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.veivel.commandr.core.MixinRelay;

import net.minecraft.client.gui.components.CommandSuggestions.SuggestionsList;

@Mixin(SuggestionsList.class)
public class SuggestionsListMixin {

  @Inject(at = @At("HEAD"), method = "useSuggestion")
  public void useSuggestion(CallbackInfo ci) {
    // TODO: this doesnt seem to work at the moment
    // on useSuggestion, clear it
    MixinRelay.clearSuggestion();
  }
}
