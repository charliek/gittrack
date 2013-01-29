package com.github.charliek.gittrack.conf

import com.fasterxml.jackson.annotation.JsonProperty

import javax.validation.constraints.NotNull

class GithubConfiguration {

    @NotNull
    @JsonProperty
    String clientID

    @NotNull
    @JsonProperty
    String clientSecret

    @NotNull
    @JsonProperty
    String applicationScope
}
