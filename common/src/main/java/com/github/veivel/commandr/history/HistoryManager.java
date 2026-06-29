package com.github.veivel.commandr.history;

import java.util.List;

/**
 * A class that stores command and message history.
 * Delegates any search logic to the HistorySearch class.
 */
public interface HistoryManager {
    public void append(String message);

    public void appendAll(List<String> message);

    public HistorySearch search(String query);
}
