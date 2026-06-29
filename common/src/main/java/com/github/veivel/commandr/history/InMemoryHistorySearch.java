package com.github.veivel.commandr.history;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InMemoryHistorySearch implements HistorySearch {

    private List<String> data;
    private String query;
    private Iterator<String> iterator;
    private List<String> prevList;
    // Required to track if the search is currently in the iterator or in the prevList.
    // TODO: evaluate if we can just scrap prevList entirely
    private int prevReverseIndex;

    public InMemoryHistorySearch(String query, List<String> list) {
        // Commandr.logger.info("Creating search of query: {}", query);
        this.query = query;
        this.data = list;
        this.iterator = data.iterator();
        this.prevReverseIndex = 0;

        this.prevList = new ArrayList<>();
    }

    @Override
    public String next() {
        if (this.prevReverseIndex > 0) {
            int index = this.prevList.size() - this.prevReverseIndex;
            this.prevReverseIndex -= 1;
            return this.prevList.get(index);
        }

        if (!this.iterator.hasNext()) {
            return null;
        }

        // Find the first next item that matches the search query
        String nextItem = this.iterator.next();
        while (nextItem != null && !nextItem.contains(query)) {
            if (!this.iterator.hasNext()) {
                return null;
            }
            nextItem = this.iterator.next();
        }

        if (nextItem == null) {
            return null;
        }
        this.prevList.add(nextItem);
        return nextItem;
    }

    @Override
    public String prev() {
        int index = this.prevList.size() - this.prevReverseIndex - 2;
        if (index < 0) {
            return null;
        }

        this.prevReverseIndex += 1;
        return this.prevList.get(index);
    }
}
