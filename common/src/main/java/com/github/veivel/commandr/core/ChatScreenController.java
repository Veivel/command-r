package com.github.veivel.commandr.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.history.HistoryManager;
import com.github.veivel.commandr.history.HistorySearch;
import com.github.veivel.commandr.mixin.ChatScreenAccessor;
import com.github.veivel.commandr.mixin.CommandSuggestionsAccessor;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;

public class ChatScreenController {
  private ChatScreenState chatScreenState;
  private HistoryManager historyManager;
  private HistorySearch historySearch;
  private CommandSuggestions commandSuggestions;

  public ChatScreenController(ChatScreenState chatScreenState, HistoryManager historyManager) {
    this.chatScreenState = chatScreenState;
    this.historyManager = historyManager;
  }

  // Shows the message as a pop-up CommandSuggestion, rather than
  // an inline auto-complete suggestion.
  public void showSuggestion(String text) {
    if (text == null || text.isBlank()) {
      return;
    }

    Screen screen = Minecraft.getInstance().screen;
    if (screen instanceof ChatScreen chatScreen) {

      CommandSuggestions commandSuggestions = ((ChatScreenAccessor) chatScreen).getCommandSuggestions();
      // EditBox editBox = ((ChatScreenInputAccessor) chatScreen).getInput();

      // Create a java.util.List of one Suggestion object
      StringRange range = new StringRange(0, text.length());
      Suggestion suggestion = new Suggestion(range, text);
      List<Suggestion> suggestionList = new ArrayList<>();
      suggestionList.add(suggestion);

      // Create a CompletableFuture returning a Suggestions object from the List<Suggestion>
      Suggestions suggestions = Suggestions.create(this.chatScreenState.getSearchQuery(), suggestionList);
      CompletableFuture<Suggestions> future = new CompletableFuture<Suggestions>();
      future.complete(suggestions);

      // Use ChatScreen's CommandSuggestions instance by casting into our own CommandSuggestionsAccessor
      Commandr.logger.info("Setting suggestion: {}", suggestion);
      ((CommandSuggestionsAccessor) commandSuggestions).setPendingSuggestions(future);
      // ((CommandSuggestionsAccessor) commandSuggestions).setSuggestions(suggestionList);
      Commandr.logger.info("Showing suggestions with visibility {}", commandSuggestions.isVisible());

      // TODO: immedateNarration?
      commandSuggestions.showSuggestions(true);
      this.commandSuggestions = commandSuggestions;
    } else {
      Commandr.logger.error("Screen is wrong class: {}", screen.getClass());
    }
  }

  public void clearSuggestion() {
    if (!this.chatScreenState.getIsOpen() || !this.chatScreenState.getIsSearching()) {
      return;
    }
    if (this.commandSuggestions == null) {
      Commandr.logger.error(
        "CommandSuggestionsAccessor does not exist. isOpen:{}, isSearching:{}, q:{}",
        this.chatScreenState.getIsOpen(),
        this.chatScreenState.getIsSearching(),
        this.chatScreenState.getSearchQuery()
      );
      return;
    }
    this.commandSuggestions.hide();
  }

  public void search() {
    if (!this.chatScreenState.getIsOpen()) {
      return;
    }
    String query = this.chatScreenState.getSearchQuery();
    if (!this.chatScreenState.getIsSearching()) {
      return;
    }
    this.historySearch = historyManager.search(query);
    String message = this.historySearch.next();
    Commandr.logger.info("searched v1, got: {}", message);
    showSuggestion(message);
  }

  public void handleActionKey() {
    // TODO: test when outside ChatScreen
    this.chatScreenState.setIsSearching(true);
    if (historySearch == null) {
      this.historySearch = historyManager.search(chatScreenState.getSearchQuery());
    }
    String message = this.historySearch.next();
    Commandr.logger.info("searched v2, got: {}", message);
    if (message == null) {
      return;
    }
    showSuggestion(message);
  }

  public void handlePrevKey() {
    // TODO: impl with this.historySearch.prev()
    return;
  }

  public void handleEscKey() {
    // TODO: impl, just disable search mode
    return;
  }

  // Delete any references to historySearch to prevent potential ConcurrentExceptions
  // TODO: does this even make a difference?
  public void clearSearch() {
    // TODO: cannot disable search mode for some reason
    Commandr.logger.info("Clearing search!");
    this.chatScreenState.setIsSearching(false);
    this.chatScreenState.setSearchQuery(""); // Not sure if this is needed
    this.historySearch = null;
  }
}
