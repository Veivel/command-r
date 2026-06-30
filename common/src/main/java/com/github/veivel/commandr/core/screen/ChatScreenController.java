package com.github.veivel.commandr.core.screen;

import com.github.veivel.commandr.Commandr;
import com.github.veivel.commandr.core.gui.MinecraftScreen;
import com.github.veivel.commandr.core.gui.SuggestionView;
import com.github.veivel.commandr.core.history.HistoryManager;
import com.github.veivel.commandr.core.history.HistorySearch;

public class ChatScreenController {

    private ChatScreenState chatScreenState;
    private HistoryManager historyManager;
    private HistorySearch historySearch;
    private Boolean isSearchEmpty;

    public ChatScreenController(
        ChatScreenState chatScreenState,
        HistoryManager historyManager
    ) {
        this.chatScreenState = chatScreenState;
        this.historyManager = historyManager;
        this.isSearchEmpty = false;
    }

    public void search(String query) {
        this.chatScreenState.setSearchQuery(query);
        if (!this.chatScreenState.getIsOpen()) {
            return;
        }
        // String query = this.chatScreenState.getSearchQuery();
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
        SuggestionView.showSuggestion(query, message);
    }

    public void handleActionKey() {
        if (!MinecraftScreen.isCurrentChatScreen()) {
            return;
        }
        this.chatScreenState.setIsSearching(true);
        if (historySearch == null) {
            this.historySearch = historyManager.search(
                this.chatScreenState.getSearchQuery()
            );
        }
        String message = this.historySearch.next();
        Commandr.logger.info("searched (action-key), got: {}", message);
        if (message == null) {
            this.isSearchEmpty = true;
            return;
        }
        this.isSearchEmpty = false;
        SuggestionView.showSuggestion(
            this.chatScreenState.getSearchQuery(),
            message
        );
    }

    public void handlePrevKey() {
        if (!MinecraftScreen.isCurrentChatScreen()) {
            return;
        }
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
        SuggestionView.showSuggestion(
            this.chatScreenState.getSearchQuery(),
            message
        );
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
