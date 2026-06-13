package com.github.veivel.commandr.history;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
  private List<String> data;
  private Integer size = 0;

  public InMemoryHistoryManager() {
    this.data = new LinkedList<String>();
  }

  @Override
  public void append(String message) {
    // TODO: set size limit
    this.data.addFirst(message);
    this.size += 1;
  }

  @Override
  public HistorySearch search(String query) {
    return new InMemoryHistorySearch(query, this.data);
  }

}
