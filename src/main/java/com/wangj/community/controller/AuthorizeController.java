package com.wangj.community.controller;

import com.wangj.community.dto.AccessTokenDTO;
import com.wangj.community.dto.GithubUser;
import com.wangj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;

    /**
     *
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("6e7f06ea113a34a9fe9d");
        accessTokenDTO.setClient_secret("878fda83bd7c0c2bb535aa2a9ca55c87de3ee985");
//        accessTokenDTO.setClient_secret("dee698e49cd39d544a1ed4465c947af5c0050510");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }

}
