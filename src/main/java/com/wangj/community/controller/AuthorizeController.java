package com.wangj.community.controller;

import com.sun.org.apache.regexp.internal.recompile;
import com.wangj.community.dto.AccessTokenDTO;
import com.wangj.community.dto.GithubUser;
import com.wangj.community.mapper.UserMapper;
import com.wangj.community.module.User;
import com.wangj.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @Autowired
    private UserMapper userMapper;

    /**
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        System.out.println("accessToken = " + accessToken);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            //登录成功，写cookie 和session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            user.setBio(githubUser.getBio());
            userMapper.insert(user);
            response.addCookie(new Cookie("token", token));
            return "redirect:/";
        } else {
            System.out.println("登录失败");
            //登录失败，重新登录
            return "redirect:/";
        }
    }

}
