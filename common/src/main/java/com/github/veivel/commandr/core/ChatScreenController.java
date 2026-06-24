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
  private Boolean isSearchEmpty;

  public ChatScreenController(ChatScreenState chatScreenState, HistoryManager historyManager) {
    this.chatScreenState = chatScreenState;
    this.historyManager = historyManager;
    this.isSearchEmpty = false;
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
      Commandr.logger.debug("Setting suggestion: {}", suggestion);
      ((CommandSuggestionsAccessor) commandSuggestions).setPendingSuggestions(future);
      Commandr.logger.debug("Showing suggestions with visibility {}", commandSuggestions.isVisible());

      // TODO: ensure immedateNarration==true doesn't cause unexpected issues
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
    Commandr.logger.info("searched (query edit), got: {}", message);
    if (message == null) {
      this.isSearchEmpty = true;
      return;
    }
    this.isSearchEmpty = false;
    showSuggestion(message);
  }

  public void handleActionKey() {
    // TODO: Fail earlier if current screen is NOT ChatScreen
    this.chatScreenState.setIsSearching(true);
    if (historySearch == null) {
      this.historySearch = historyManager.search(chatScreenState.getSearchQuery());
    }
    String message = this.historySearch.next();
    Commandr.logger.info("searched (action-key), got: {}", message);
    if (message == null) {
      this.isSearchEmpty = true;
      return;
    }
    this.isSearchEmpty = false;
    showSuggestion(message);
  }

  public void handlePrevKey() {
    // TODO: Fail earlier if current screen is NOT ChatScreen
    if (historySearch == null) {
      return;
    }
    String message = this.historySearch.prev();
    Commandr.logger.info("searched (prev-key), got: {}", message);
    if (message == null) {
      // Don't set isSearchEmpty as true here
      // since we've returned to the start
      return;
    }
    this.isSearchEmpty = false;
    showSuggestion(message);
  }

  public void handleEscKey() {
    // TODO: impl, just disable search mode
    return;
  }

  public void clearSearch() {
    Commandr.logger.debug("Clearing search!");
    this.chatScreenState.setIsSearching(false);
    this.chatScreenState.setSearchQuery(""); // Not sure if this is needed
    
    // Delete any references to historySearch to prevent ConcurrentModificationException, 
    // which is caused by a data structure being modified while there is a live iterator 
    // over it.
    this.historySearch = null;
  }

  public Boolean getIsSearchEmpty() {
    return this.isSearchEmpty;
  }
}
