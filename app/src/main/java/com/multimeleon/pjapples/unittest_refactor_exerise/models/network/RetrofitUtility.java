package com.multimeleon.pjapples.unittest_refactor_exerise.models.network;

public class RetrofitUtility {
    private static final String BASE_URL = "https://api.github.com";
    public static GitHubApi getGitHubApi(){
        return RetrofitClient.getClient(BASE_URL).create(GitHubApi.class);
    }
}
