package com.list.user.githubuser.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.list.user.githubuser.service.GithubUserService;

@RestController
@RequestMapping("/api")
public class GithubUserController {
    private GithubUserService githubUserService;

    public GithubUserController(GithubUserService githubUserService) {
        this.githubUserService = githubUserService;
    }

    @GetMapping(value = "/github-users", produces = MediaType.APPLICATION_PDF_VALUE)
    public byte[] getGithubUser() throws IOException, JSONException, DocumentException{
        return githubUserService.getGithubUser();
    }
}
