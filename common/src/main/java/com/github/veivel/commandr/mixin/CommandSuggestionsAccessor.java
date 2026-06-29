package com.github.veivel.commandr.mixin;

import com.mojang.brigadier.suggestion.Suggestions;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.components.CommandSuggestions.SuggestionsList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CommandSuggestions.class)
public interface CommandSuggestionsAccessor {
    @Accessor("pendingSuggestions")
    void setPendingSuggestions(CompletableFuture<Suggestions> suggestions);

    @Accessor("suggestions")
    void setSuggestions(SuggestionsList suggestions);
}
