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
public class ChatScreenState {

  private boolean isOpen;
  private boolean isSearching;
  private String searchQuery = "";

  public ChatScreenState() {
    this.isOpen = false;
    this.isSearching = false;
  }

  public boolean getIsOpen() {
    return this.isOpen;
  }

  public void setIsOpen(Boolean val) {
    if (!val) {
      // Also disable search mode when closing ChatScreen
      this.isSearching = false;
    }
    this.isOpen = val;
  }

  public boolean getIsSearching() {
    return this.isSearching;
  }

  public void setIsSearching(Boolean val) {
    this.isSearching = val;
    if (this.getIsOpen()) {
      Commandr.logger.info("search mode:", this.isSearching);
    }
  }

  public String getSearchQuery() {
    return this.searchQuery;
  }

  public void setSearchQuery(String query) {
    this.searchQuery = query;
  }
}
