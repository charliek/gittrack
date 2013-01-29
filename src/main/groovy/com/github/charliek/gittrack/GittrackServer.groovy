package com.github.charliek.gittrack

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.charliek.gittrack.auth.GithubUserProvider
import com.github.charliek.gittrack.conf.GittrackConfiguration
import com.github.charliek.gittrack.github.GithubService
import com.github.charliek.gittrack.resources.OAuthResource
import com.github.charliek.gittrack.resources.RepoResource
import com.yammer.dropwizard.Service
import com.yammer.dropwizard.assets.AssetsBundle
import com.yammer.dropwizard.client.HttpClientBuilder
import com.yammer.dropwizard.config.Bootstrap
import com.yammer.dropwizard.config.Environment
import com.yammer.dropwizard.views.ViewBundle
import org.apache.http.client.HttpClient

class GittrackServer extends Service<GittrackConfiguration> {

    public static void main(String[] args) throws Exception {
        new GittrackServer().run(args)
    }

    @Override
    void initialize(Bootstrap<GittrackConfiguration> bootstrap) {
        bootstrap.name = 'gittrack_service'
        bootstrap.addBundle(new ViewBundle())
        bootstrap.addBundle(new AssetsBundle('/static'))
    }

    @Override
    void run(GittrackConfiguration config, Environment environment) {
        HttpClient httpClient = new HttpClientBuilder().using(config.httpClient).build();
        ObjectMapper mapper = environment.objectMapperFactory.build()

        GithubService githubService = new GithubService(httpClient, mapper, config.github)
        GithubUserProvider userProvider = new GithubUserProvider(githubService)
        environment.addProvider(userProvider)

        // Setup resource objects
        environment.addResource(new RepoResource(githubService))
        environment.addResource(new OAuthResource(githubService))
    }
}
