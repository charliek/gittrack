package com.github.charliek.gittrack.transfer

import com.yammer.dropwizard.json.JsonSnakeCase
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
@JsonSnakeCase
class Issue {
    PullRequest pullRequest
    String url
    String state
    List<Label> labels
    String title
    int number
    String eventsUrl
    // assignee // TODO determine type
    String updatedAt
    Repository repository
    User user
    Integer comments
    String createdAt
    String htmlUrl
    Integer id
    String commentsUrl
    String body
}
