package com.github.charliek.gittrack.auth

import com.github.charliek.gittrack.github.GithubService
import com.github.charliek.gittrack.transfer.User
import com.google.common.base.Optional
import com.sun.jersey.api.core.HttpContext
import com.sun.jersey.api.model.Parameter
import com.sun.jersey.core.spi.component.ComponentContext
import com.sun.jersey.core.spi.component.ComponentScope
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable
import com.sun.jersey.spi.inject.Injectable
import com.sun.jersey.spi.inject.InjectableProvider

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Response

public class GithubUserProvider implements InjectableProvider<GithubAuth, Parameter> {
    private static class GithubUserInjectable extends AbstractHttpContextInjectable<User> {
        private final boolean required
        private final GithubService githubService

        private GithubUserInjectable(GithubService githubService, boolean required) {
            this.githubService = githubService
            this.required = required
        }

        @Override
        public User getValue(HttpContext c) {
            String authToken = c.request.cookies.get('github_access_token')?.value
            if (authToken != null) {
                // TODO need to introduce caching here
                Optional<User> user = githubService.lookupUser(authToken)
                if (user.isPresent()) {
                    User u = user.get()
                    u.authToken = authToken
                    return u
                }
            } else if (required) {
                Response response = Response
                        .temporaryRedirect(githubService.authUrl.toURI())
                        .build()
                throw new WebApplicationException(response)
            }
            return null
        }
    }

    private GithubService githubService

    public GithubUserProvider(GithubService githubService) {
        this.githubService = githubService
    }

    @Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

    @Override
    Injectable getInjectable(ComponentContext ic, GithubAuth a, Parameter c) {
        return new GithubUserInjectable(githubService, a.required());
    }
}
