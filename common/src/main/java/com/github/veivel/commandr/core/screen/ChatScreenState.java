package com.github.veivel.commandr.core.screen;

import com.github.veivel.commandr.Commandr;

/**
 * Manages the state of ChatScreen:
 * https://mcsrc.dev/1/26.1.2/net/minecraft/client/gui/screens/ChatScreen
 */
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
