package com.multimeleon.pjapples.unittest_refactor_exerise.models;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.multimeleon.pjapples.unittest_refactor_exerise.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private ReposRvAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final GitHubApi gitHubApi = retrofit.create(GitHubApi.class);

        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchGitHubRepos(gitHubApi, etSearchQuery.getText().toString());
                    return true;
                }
                return false;
            }
        });

        RecyclerView rvRepos = findViewById(R.id.rv_repos);
        rvAdapter = new ReposRvAdapter();
        rvRepos.setHasFixedSize(true);
        rvRepos.setAdapter(rvAdapter);
    }

    /**
     * @param gitHubApi Retrofit interface to fetch data from GitHub
     * @param query     search query e.g. "android view stars:>1000 topic:android"
     */
    private void searchGitHubRepos(GitHubApi gitHubApi,
                                   String query) {
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


    private void handleResponse(@NonNull Response<SearchResponse> response) {
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

    private void handleError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void handleSearchResults(List<SearchResult> searchResults) {
        rvAdapter.updateResults(searchResults);
    }
}
