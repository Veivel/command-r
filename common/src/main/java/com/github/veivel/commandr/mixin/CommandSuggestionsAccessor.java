package com.github.veivel.commandr.mixin;

import java.util.concurrent.CompletableFuture;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import com.mojang.brigadier.suggestion.Suggestions;

import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.CommandSuggestions.SuggestionsList;

@Mixin(CommandSuggestions.class)
public interface CommandSuggestionsAccessor {

  @Accessor("pendingSuggestions")
  void setPendingSuggestions(CompletableFuture<Suggestions> suggestions);

  @Accessor("suggestions")
  void setSuggestions(SuggestionsList suggestions);
}
