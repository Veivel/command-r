package com.github.veivel.commandr.core;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.history.HistoryManager;

public final class MixinRelay {
  public static ChatScreenState chatScreenState;
  public static HistoryManager historyManager;

  public static void init(ChatScreenState chatScreenState, HistoryManager historyManager) {
    MixinRelay.chatScreenState = chatScreenState;
    MixinRelay.historyManager = historyManager;
  }

  public static void addToHistory(String message) {
    Commandr.logger.info("adding to history: {}", message);
    historyManager.append(message);
  }

  public static Boolean inSearchMode() {
    return chatScreenState.inSearchMode();
  }

  public static void setChatScreenStatus(Boolean isOpen) {
    Commandr.logger.info("ChatScreen open status: {}", isOpen);
    chatScreenState.setOpen(isOpen);
  }

  public static void setChatScreenQuery(String query) {
    Commandr.logger.info("search query: {}", query);
    chatScreenState.setSearchQuery(query);
  }
}
