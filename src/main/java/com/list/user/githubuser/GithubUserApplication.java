package com.list.user.githubuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.list.user.githubuser")
public class GithubUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubUserApplication.class, args);
	}

}
