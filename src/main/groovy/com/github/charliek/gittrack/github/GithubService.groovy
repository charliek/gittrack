package com.github.charliek.gittrack.github

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.charliek.gittrack.conf.GithubConfiguration
import com.github.charliek.gittrack.transfer.AccessToken
import com.github.charliek.gittrack.transfer.Issue
import com.github.charliek.gittrack.transfer.User
import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.apache.http.HttpResponse
import org.apache.http.NameValuePair
import org.apache.http.client.HttpClient
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair

import javax.ws.rs.core.MediaType

@Slf4j
class GithubService {

    private HttpClient client
    private ObjectMapper mapper
    private GithubConfiguration config
    private static final String ACCEPT_HEADER = 'Accept'

    GithubService(HttpClient client, ObjectMapper mapper, GithubConfiguration config) {
        this.client = client
        this.mapper = mapper
        this.config = config
    }

    AccessToken lookupAccessToken(String code) {
        String url = 'https://github.com/login/oauth/access_token'
        HttpPost request = new HttpPost(url)
        request.setHeader(ACCEPT_HEADER, MediaType.APPLICATION_JSON)
        List <NameValuePair> nvps = []
        nvps << new BasicNameValuePair('client_id', config.clientID)
        nvps << new BasicNameValuePair('client_secret', config.clientSecret)
        nvps << new BasicNameValuePair('code', code)
        request.setEntity(new UrlEncodedFormEntity(nvps));
        HttpResponse response = client.execute(request)
        String body = response.entity.content.text
        log.info("User call response: ${body}")
        return mapper.readValue(body, AccessToken)
    }

    Optional<User> lookupUser(String token) {
        HttpGet request = new HttpGet("https://api.github.com/user?access_token=${token}")
        request.setHeader(ACCEPT_HEADER, MediaType.APPLICATION_JSON)
        HttpResponse response = client.execute(request)
        String body = response.entity.content.text
        log.info("User call response: ${body}")
        User user = mapper.readValue(body, User)
        log.info("Found user: ${user}")
        return Optional.fromNullable(user)
    }

    List<Issue> lookupIssues(String token) {
        String url = "https://api.github.com/issues?filter=all&access_token=${token}"
        HttpGet request = new HttpGet(url)
        request.setHeader(ACCEPT_HEADER, MediaType.APPLICATION_JSON)
        HttpResponse response = client.execute(request)
        String body = response.entity.content.text
        log.info("Issues call response: ${body}")
        try {
            List<Issue> issues = mapper.readValue(body, new TypeReference<List<Issue>>(){})
            return issues
        } catch (IOException e) {
            log.warn("Error when accessing url ${url}", e)
            throw new GithubException("Error when accessing url ${url}", e)
        }
    }

    String getAuthUrl() {
        return "https://github.com/login/oauth/authorize?client_id=${config.clientID}&scope=${config.applicationScope}"
    }

}
