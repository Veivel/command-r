package com.github.veivel.commandr.history;

/**
 * A class that stores command & message history.
 * Delegates any search logic to the HistorySearch class.
 */
public interface HistoryManager {

  public void append(String message);

  public HistorySearch search(String query);
}
