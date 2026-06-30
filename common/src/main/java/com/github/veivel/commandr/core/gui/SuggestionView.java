package com.github.veivel.commandr.core.gui;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.mixin.ChatScreenAccessor;
import com.github.veivel.commandr.mixin.CommandSuggestionsAccessor;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;

// TODO: make not static

public class SuggestionView {

    private static CommandSuggestions commandSuggestions;

    // Shows the message as a pop-up CommandSuggestion, rather than
    // an inline auto-complete suggestion.
    public static void showSuggestion(
        String searchQuery,
        String suggestionText
    ) {
        if (suggestionText == null || suggestionText.isBlank()) {
            return;
        }

        Screen screen = MinecraftScreen.getCurrent();
        if (screen instanceof ChatScreen chatScreen) {
            CommandSuggestions commandSuggestions = (
                (ChatScreenAccessor) chatScreen
            ).getCommandSuggestions();

            // Create a java.util.List of one Suggestion object
            StringRange range = new StringRange(0, suggestionText.length());
            Suggestion suggestion = new Suggestion(range, suggestionText);
            List<Suggestion> suggestionList = new ArrayList<>();
            suggestionList.add(suggestion);

            // Create a CompletableFuture returning a Suggestions object from the List<Suggestion>
            Suggestions suggestions = Suggestions.create(
                searchQuery,
                suggestionList
            );
            CompletableFuture<Suggestions> future = new CompletableFuture<
                Suggestions
            >();
            future.complete(suggestions);

            // Use ChatScreen's CommandSuggestions instance by casting into our own CommandSuggestionsAccessor
            Commandr.logger.debug("Setting suggestion: {}", suggestion);
            (
                (CommandSuggestionsAccessor) commandSuggestions
            ).setPendingSuggestions(future);
            Commandr.logger.debug(
                "Showing suggestions with visibility {}",
                commandSuggestions.isVisible()
            );

            // TODO: ensure immedateNarration==true doesn't cause unexpected issues
            commandSuggestions.showSuggestions(true);
            SuggestionView.commandSuggestions = commandSuggestions;
        } else {
            Commandr.logger.error(
                "Screen is wrong class: {}",
                screen.getClass()
            );
        }
    }

    /**
     * Runs after useSuggestion is called.
     * This clears the shown suggestions and disables search mode.
     */
    public static void onUseSuggestion() {
        if (SuggestionView.commandSuggestions == null) {
            Commandr.logger.error("CommandSuggestionsAccessor does not exist.");
            return;
        }
        // Clear suggestions
        SuggestionView.commandSuggestions.hide();
    }
}
