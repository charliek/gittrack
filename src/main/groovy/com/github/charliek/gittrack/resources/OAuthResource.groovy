package com.github.charliek.gittrack.resources

import com.github.charliek.gittrack.github.GithubService
import com.github.charliek.gittrack.transfer.AccessToken
import groovy.util.logging.Slf4j

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.NewCookie
import javax.ws.rs.core.Response

@Path('/auth')
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
class OAuthResource {

    private GithubService githubService

    public OAuthResource(GithubService githubService) {
        this.githubService = githubService
    }

    @GET
    public Response authenticate(@QueryParam('code') String code) {
        log.info("Looking up code ${code}")
        AccessToken token = githubService.lookupAccessToken(code)
        if (token?.accessToken != null) {
            NewCookie tokenCookie = new NewCookie('github_access_token', token.accessToken)
            return Response
                    .temporaryRedirect('/'.toURI())
                    .cookie(tokenCookie)
                    .build()
        }
        return Response
                .temporaryRedirect(githubService.authUrl.toURI())
                .build()
    }

}
