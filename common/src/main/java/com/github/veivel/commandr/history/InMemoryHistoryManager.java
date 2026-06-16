package com.github.veivel.commandr.history;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
  private List<String> data;
  private Integer size = 0;
  private Integer sizeLimit;

  public InMemoryHistoryManager() {
    int DEFAULT_SIZE_LIMIT = 100;
    this(DEFAULT_SIZE_LIMIT);
  }

  public InMemoryHistoryManager(Integer sizeLimit) {
    this.sizeLimit = sizeLimit;
    this.data = new LinkedList<String>();
  }

  @Override
  public void append(String message) {
    if (this.size >= sizeLimit) {
      this.data.removeLast();
      this.size -= 1;
    }
    this.data.addFirst(message);
    this.size += 1;
  }

  @Override
  public void appendAll(List<String> messages) {
    // ASSUME: `messages` is ordered from oldest first to newest last.

    // If too many messages to be appended, truncate `messages`
    if (messages.size() > this.sizeLimit){
      messages = messages.subList(messages.size() - this.sizeLimit, messages.size());
    }
    // If total size after append will be too large, truncate current data
    if (this.size + messages.size() > this.sizeLimit) {
      this.data = this.data.subList(0, this.sizeLimit - messages.size());
    }
    this.data.addAll(0, messages.reversed()); // TODO: ensure Minecraft history is actually oldest-first->newest-last
    this.size += messages.size();
  }

  @Override
  public HistorySearch search(String query) {
    return new InMemoryHistorySearch(query, this.data);
  }

}
