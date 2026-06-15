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
    // TODO: set size limit, fixed to 100 because of Minecraft loading logic
    this.data.addFirst(message);
    this.size += 1;
  }

  @Override
  public void appendAll(List<String> messages) {
    // TODO: set size limit

    // TODO: verify this reversing is correct
    this.data.addAll(0, messages.reversed());
    this.size += messages.size();
  }

  @Override
  public HistorySearch search(String query) {
    return new InMemoryHistorySearch(query, this.data);
  }

}
