package com.wangj.community.provider;


import com.alibaba.fastjson.JSON;
import com.wangj.community.dto.AccessTokenDTO;
import com.wangj.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.Companion.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            String string = response.body().string();
            String string = "{\"login\":\"sikeziji\",\"id\":29722242,\"node_id\":\"MDQ6VXNlcjI5NzIyMjQy\",\"avatar_url\":\"https://avatars3.githubusercontent.com/u/29722242?v=4\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/sikeziji\",\"html_url\":\"https://github.com/sikeziji\",\"followers_url\":\"https://api.github.com/users/sikeziji/followers\",\"following_url\":\"https://api.github.com/users/sikeziji/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/sikeziji/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/sikeziji/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/sikeziji/subscriptions\",\"organizations_url\":\"https://api.github.com/users/sikeziji/orgs\",\"repos_url\":\"https://api.github.com/users/sikeziji/repos\",\"events_url\":\"https://api.github.com/users/sikeziji/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/sikeziji/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":\"死磕自己\",\"company\":null,\"blog\":\"\",\"location\":null,\"email\":null,\"hireable\":null,\"bio\":\"任何一个傻瓜都会写能够让机器理解的代码,只有好的程序员才能写出人类可以理解的代码\",\"public_repos\":33,\"public_gists\":0,\"followers\":1,\"following\":2,\"created_at\":\"2017-06-27T03:58:28Z\",\"updated_at\":\"2020-03-25T00:37:02Z\",\"private_gists\":0,\"total_private_repos\":0,\"owned_private_repos\":0,\"disk_usage\":36784,\"collaborators\":0,\"" +
                    "two_factor_authentication\":false," +
                    "\"plan\":{\"name\":\"free\"," +
                    "\"space\":976562499,\"collaborators\"" +
                    ":0,\"private_repos\":10000}}";
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            System.out.println("githubUser"+ githubUser == null);
            System.out.println("githubUser"+ githubUser.getAvatarUrl());
            System.out.println("githubUser"+ githubUser.getId());
            System.out.println("githubUser"+ githubUser.getBio());
            System.out.println("githubUser"+ githubUser.getName());
            return githubUser;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }


}
