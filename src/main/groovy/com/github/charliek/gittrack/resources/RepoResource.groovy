package com.github.charliek.gittrack.resources

import com.github.charliek.gittrack.auth.GithubAuth
import com.github.charliek.gittrack.github.GithubService
import com.github.charliek.gittrack.transfer.Issue
import com.github.charliek.gittrack.transfer.User
import com.github.charliek.gittrack.view.IndexView
import com.yammer.dropwizard.views.View

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path('/')
@Produces(MediaType.TEXT_HTML)
class RepoResource {

    GithubService githubService

    RepoResource(GithubService githubService) {
        this.githubService = githubService
    }

    @GET
    public View listRepositories(@GithubAuth User user) {
        List<Issue> issues = githubService.lookupIssues(user.authToken)
        return new IndexView(issues)
    }

}
