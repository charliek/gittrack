package com.github.charliek.gittrack.conf

import com.fasterxml.jackson.annotation.JsonProperty
import com.yammer.dropwizard.client.HttpClientConfiguration
import com.yammer.dropwizard.config.Configuration
import com.yammer.dropwizard.db.DatabaseConfiguration

import javax.validation.Valid
import javax.validation.constraints.NotNull

class GittrackConfiguration extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    GithubConfiguration github = new GithubConfiguration()

    @Valid
    @NotNull
    @JsonProperty
    HttpClientConfiguration httpClient = new HttpClientConfiguration()

    @Valid
    @NotNull
    @JsonProperty
    DatabaseConfiguration database = new DatabaseConfiguration()
}
