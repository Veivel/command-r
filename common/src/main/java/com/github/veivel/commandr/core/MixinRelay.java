package com.github.veivel.commandr.core;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.history.HistoryManager;
import java.util.List;

public final class MixinRelay {

    public static ChatScreenState chatScreenState;
    // TODO: refactor Controller to "own" state, so we can move state out of this class
    public static ChatScreenController chatScreenController;
    public static HistoryManager historyManager;

    public static void init(
        ChatScreenState chatScreenState,
        ChatScreenController chatScreenController,
        HistoryManager historyManager
    ) {
        MixinRelay.chatScreenState = chatScreenState;
        MixinRelay.historyManager = historyManager;
        MixinRelay.chatScreenController = chatScreenController;
    }

    public static void addAllToHistory(List<String> messages) {
        Commandr.logger.debug("adding to history: {} items", messages.size());
        historyManager.appendAll(messages);
    }

    public static void addToHistory(String message) {
        Commandr.logger.debug("adding to history: {}", message);
        historyManager.append(message);
    }

    public static Boolean inSearchMode() {
        return chatScreenState.getIsSearching();
    }

    public static void onUseSuggestion() {
        chatScreenController.onUseSuggestion();
    }

    public static void setChatScreenStatus(Boolean isOpen) {
        Commandr.logger.debug("ChatScreen open status: {}", isOpen);

        chatScreenState.setIsOpen(isOpen);
        if (!isOpen) {
            chatScreenController.clearSearch();
        }
    }

    public static void setChatScreenQuery(String query) {
        Commandr.logger.debug("Setting search query: {}", query);
        chatScreenController.search(query);
    }

    public static boolean isSearchEmpty() {
        return chatScreenController.getIsSearchEmpty();
    }
}
