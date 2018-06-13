package com.multimeleon.pjapples.unittest_refactor_exerise.models.search;

import android.support.annotation.NonNull;

import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResponse;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.network.GitHubApi;

import retrofit2.Response;

public interface SearchPresenter {
    void searchGitHubRepos(GitHubApi gitHubApi, String query);
    void handleResponse(@NonNull Response<SearchResponse> response);
    void handleError(String s);
    void onDestroy();
}
