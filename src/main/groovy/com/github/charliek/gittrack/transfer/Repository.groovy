package com.github.charliek.gittrack.transfer

import com.fasterxml.jackson.annotation.JsonProperty
import com.yammer.dropwizard.json.JsonSnakeCase
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
@JsonSnakeCase
class Repository {
    Boolean hasDownloads
    String subscriptionUrl
    String languagesUrl
    String teamsUrl
    String eventsUrl
    User owner
    Integer forksCount
    String milestonesUrl
    String gitCommitsUrl
    String updatedAt
    String fullName

    @JsonProperty('private')
    Boolean privateRepo
    Integer forks
}
