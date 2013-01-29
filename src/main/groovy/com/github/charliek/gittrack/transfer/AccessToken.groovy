package com.github.charliek.gittrack.transfer

import com.yammer.dropwizard.json.JsonSnakeCase
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
@JsonSnakeCase
class AccessToken {
    String accessToken
    String tokenType
}
