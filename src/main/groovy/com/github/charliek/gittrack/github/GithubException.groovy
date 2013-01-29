package com.github.charliek.gittrack.github

class GithubException extends IOException {
    GithubException(String s, Throwable throwable) {
        super(s, throwable)
    }
}
