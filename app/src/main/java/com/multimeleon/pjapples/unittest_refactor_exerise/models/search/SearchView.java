package com.multimeleon.pjapples.unittest_refactor_exerise.models.search;

import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResult;

import java.util.List;

public interface SearchView {
    void showSearchResults(List<SearchResult> searchResults);
    void showError(String s);
}
