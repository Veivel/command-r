package com.github.veivel.commandr.history;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {

  private InMemoryHistoryManager history;

  @BeforeEach
  void setUp() {
    history = new InMemoryHistoryManager();
  }

  @Test
  void searchOnEmptyHistoryHasNoResults() {
    HistorySearch searchResult = history.search("something");
    assert searchResult.hasNext() == false;
  }

  @Test
  void searchReturnsNewestMatchingEntry() {
    history.append("something is not wrong");
    history.append("something is wrong");
    HistorySearch searchResult = history.search("somethi");

    assert searchResult.hasNext();
    assert searchResult.next() == "something is wrong";
  }

  @Test
  void searchFiltersOutNonMatchingEntries() {
    history.append("something is not wrong");
    history.append("this shouldn't match");
    history.append("something is wrong");
    history.append("this shouldn't match either");
    history.append("something");
    history.append("somethingi");
    history.append("something nice");
    // history.append("eat something i"); // TODO: reinsert after we fix the logic

    HistorySearch searchResult = history.search("something i");

    assert searchResult.next() == "something is wrong";
    assert searchResult.next() == "something is not wrong";
    assert searchResult.hasNext() == false;
    assert searchResult.next() == null;
  }


  @Test
  void prevReturnsLastVisitedResult() {
    history.append("x");
    history.append("something is not wrong");
    history.append("x");
    history.append("something is wrong");
    history.append("x");
    history.append("something is correct");
    history.append("x");

    HistorySearch searchResult = history.search("s");

    assert searchResult.next() == "something is correct";
    assert searchResult.next() == "something is wrong";
    assert searchResult.next() == "something is not wrong";
    assert searchResult.prev() == "something is wrong";
    assert searchResult.next() == "something is not wrong";
    assert searchResult.prev() == "something is wrong";
    assert searchResult.prev() == "something is correct";
    assert searchResult.prev() == null;
  }

  @Test
  void hasNextReturnsFalseWhenExhausted() {
  }
}
