package com.multimeleon.pjapples.unittest_refactor_exerise.models.search;

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
import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResponse;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.model.SearchResult;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.network.GitHubApi;
import com.multimeleon.pjapples.unittest_refactor_exerise.models.network.RetrofitUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements SearchView {

    private ReposRvAdapter rvAdapter;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSearchQuery = findViewById(R.id.et_search_query);
        searchPresenter = new SearchPresenterImpl(this);

        final GitHubApi gitHubApi = RetrofitUtility.getGitHubApi();
        etSearchQuery.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchPresenter.searchGitHubRepos(gitHubApi, etSearchQuery.getText().toString());
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

    @Override
    public void showSearchResults(List<SearchResult> searchResults) {
        rvAdapter.updateResults(searchResults);
    }

    @Override
    public void showError(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    @Override protected void onDestroy(){
        searchPresenter.onDestroy();;
        super.onDestroy();
    }
}