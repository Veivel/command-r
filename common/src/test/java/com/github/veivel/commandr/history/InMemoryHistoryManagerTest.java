package com.github.veivel.commandr.history;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager history;

    @BeforeEach
    void setUp() {
        history = new InMemoryHistoryManager();
    }

    @Test
    void searchOnEmptyHistoryReturnsNull() {
        HistorySearch searchResult = history.search("something");
        assert searchResult.next() == null;
    }

    @Test
    void searchReturnsNewestMatchingEntry() {
        history.append("something is not wrong");
        history.append("something is wrong");
        HistorySearch searchResult = history.search("somethi");

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
        history.append("eat something ice");
        history.append("eat something nice please");

        HistorySearch searchResult = history.search("something i");

        assert searchResult.next() == "eat something ice";
        assert searchResult.next() == "something is wrong";
        assert searchResult.next() == "something is not wrong";
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
    void searchReturnsNullWhenExhausted() {
        history.append("x");
        history.append("xz");
        history.append("y");
        history.append("z");

        HistorySearch searchResult = history.search("z");

        assert searchResult.next() == "z";
        assert searchResult.next() == "xz";
        assert searchResult.next() == null;
    }

    @Test
    void appendThenAppendAllOrdersNewestFirst() {
        history.append("msg_old");
        history.appendAll(List.of("msg_newer", "msg_newest"));

        HistorySearch result = history.search("msg_");

        assert result.next() == "msg_newest";
        assert result.next() == "msg_newer";
        assert result.next() == "msg_old";
        assert result.next() == null;
    }

    @Test
    void appendAllTruncatesWhenExceedsLimit() {
        InMemoryHistoryManager limitedHistory = new InMemoryHistoryManager(2);
        limitedHistory.appendAll(
            List.of("item_oldest", "item_middle", "item_newest")
        );

        HistorySearch result = limitedHistory.search("item_");

        assert result.next() == "item_newest";
        assert result.next() == "item_middle";
        assert result.next() == null;
    }

    @Test
    void appendAllEvictsOldestWhenAtLimit() {
        InMemoryHistoryManager limitedHistory = new InMemoryHistoryManager(3);
        limitedHistory.append("item_a");
        limitedHistory.append("item_b");
        limitedHistory.append("item_c");
        limitedHistory.appendAll(List.of("item_d", "item_e"));

        HistorySearch result = limitedHistory.search("item_");

        assert result.next() == "item_e";
        assert result.next() == "item_d";
        assert result.next() == "item_c";
        assert result.next() == null;
    }

    @Test
    void appendEvictsOldestWhenAtLimit() {
        InMemoryHistoryManager limitedHistory = new InMemoryHistoryManager(3);
        limitedHistory.append("item_a");
        limitedHistory.append("item_b");
        limitedHistory.append("item_c");
        limitedHistory.append("item_d");

        HistorySearch result = limitedHistory.search("item_");

        assert result.next() == "item_d";
        assert result.next() == "item_c";
        assert result.next() == "item_b";
        assert result.next() == null;
    }

    @Test
    void appendSizeRemainsAtLimitAfterRepeatedEvictions() {
        InMemoryHistoryManager limitedHistory = new InMemoryHistoryManager(3);
        limitedHistory.append("item_a");
        limitedHistory.append("item_b");
        limitedHistory.append("item_c");
        limitedHistory.append("item_d");
        limitedHistory.append("item_e");

        HistorySearch result = limitedHistory.search("item_");

        assert result.next() == "item_e";
        assert result.next() == "item_d";
        assert result.next() == "item_c";
        assert result.next() == null;
    }
}
