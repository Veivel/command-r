package com.github.veivel.commandr.history;

/**
 * Classes that implement this interface are classes performing
 * "lazy" search that can be interacted with like a 2-way iterator.
 * This class is instantiated for every new or modified search query.
 *
 * Search query is case sensitive and looks for any/partial matches.
 */
public interface HistorySearch {
    public String next();

    public String prev();

    // public Boolean hasNext();
}
