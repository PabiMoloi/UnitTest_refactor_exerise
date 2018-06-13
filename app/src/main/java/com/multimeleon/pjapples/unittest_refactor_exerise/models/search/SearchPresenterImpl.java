package com.multimeleon.pjapples.unittest_refactor_exerise.models.search;

import android.support.annotation.NonNull;
import android.util.Log;

import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResponse;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResult;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.network.GitHubApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchPresenterImpl implements SearchPresenter, OnSearchFinished{
    private SearchView searchView;

    SearchPresenterImpl(SearchView searchView) {
        this.searchView = searchView;
    }

    @Override
    public void searchGitHubRepos(GitHubApi gitHubApi, String query) {
        Call<SearchResponse> call = gitHubApi.searchRepos(query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call,
                                   Response<SearchResponse> response) {
                handleResponse(response);
            }

            @Override
            public void onFailure(Call<SearchResponse> call,
                                  Throwable t) {
                Log.e("", "", t);
                handleError("E103 - System error");
            }
        });
    }

    @Override
    public void onDestroy() {
        searchView = null;
    }

    @Override
    public void handleError(String s) {
        searchView.showError(s);
    }

    @Override
    public void handleResponse(@NonNull Response<SearchResponse> response) {
        if (response.isSuccessful()) {
            SearchResponse searchResponse = response.body();
            if (searchResponse != null) {
                handleSearchResults(searchResponse.getSearchResults());
            } else {
                handleError("E102 - System error");
            }
        } else {
            handleError("E101 - System error");
        }
    }

    @Override
    public void handleSearchResults(List<SearchResult> searchResults) {
        searchView.showSearchResults(searchResults);
    }
}
