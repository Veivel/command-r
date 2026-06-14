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

// Manages the state of ChatScreen:
// https://mcsrc.dev/1/26.1.2/net/minecraft/client/gui/screens/ChatScreen

// TODO: refactor this class into two: dumb state & blind controller
public class ChatScreenState {

  // Singleton is used because Mixins interact with this class
  // but we cannot control the lifecycle of Mixins.
  private static ChatScreenState INSTANCE;
  private boolean open;
  private boolean searching;
  private String searchQuery = "";

  private HistoryManager historyManager;
  private HistorySearch historySearch;

  public static ChatScreenState getInstance() {
    return INSTANCE;
  }

  public ChatScreenState(HistoryManager historyManager) {
    this.historyManager = historyManager;
    this.open = false;
    this.searching = false;
    INSTANCE = this;
  }

  private void replaceSuggestion(String text) {
    if (text == null || text.isBlank()) {
      return;
    }

    // TODO: refactor Minecraft imports outta this class
    Screen screen = Minecraft.getInstance().screen;
    if (screen instanceof ChatScreen chatScreen) {
      // EditBox editBox = ((ChatScreenInputAccessor) chatScreen).getInput();
      CommandSuggestions commandSuggestions = ((ChatScreenAccessor) chatScreen).getCommandSuggestions();

      StringRange range = new StringRange(0, text.length());
      Suggestion suggestion = new Suggestion(range, text);
      List<Suggestion> suggestionList = new ArrayList<>();
      suggestionList.add(suggestion);

      Suggestions suggestions = Suggestions.create(this.searchQuery, suggestionList);
      CompletableFuture<Suggestions> future = new CompletableFuture<Suggestions>();
      future.complete(suggestions);

      Commandr.logger.info("Setting suggestion: {}", suggestion);
      ((CommandSuggestionsAccessor) commandSuggestions).setPendingSuggestions(future);
      // ((CommandSuggestionsAccessor) commandSuggestions).setSuggestions(suggestionList);
      Commandr.logger.info("Showing suggestions with visibility {}", commandSuggestions.isVisible());
      commandSuggestions.showSuggestions(true);
    } else {
      Commandr.logger.error("Screen is wrong class: {}", screen.getClass());
    }
  }

  public void setSearchQuery(String query) {
    if (this.isOpen() && this.inSearchMode()) {
      this.searchQuery = query;
      this.historySearch = historyManager.search(query);
      String message = this.historySearch.next();
      Commandr.logger.info("searched v1, got: {}", message);

      String suggestion = message;
      replaceSuggestion(suggestion);
    }
  }

  public void handleActionKey() {
    if (!this.inSearchMode()) {
      this.toggleSearchMode();
    }

    if (historySearch == null) {
      this.historySearch = historyManager.search(this.searchQuery);
    }

    String message = this.historySearch.next();
    Commandr.logger.info("searched v2, got: {}", message);

    if (message == null) {
      return;
    }

    String suggestion = message;
    replaceSuggestion(suggestion);
  }

  public void setOpen(boolean val) {
    if (!val) {
      // Also disable search mode when closing ChatScreen
      this.searching = false;
      this.historySearch = null;
    }
    this.open = val;
  }

  public boolean isOpen() {
    return this.open;
  }

  public void toggleSearchMode() {
    if (!this.isOpen()) {
      // Don't do anything if ChatScreen is closed
      return;
    }

    this.searching = !this.inSearchMode();
    Commandr.logger.info("search mode:", this.searching);
  }

  public boolean inSearchMode() {
    return this.searching;
  }
}
