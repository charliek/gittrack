package com.github.charliek.gittrack.view

import com.github.charliek.gittrack.transfer.Issue
import com.yammer.dropwizard.views.View

class IndexView extends View {

    List<Issue> issues
    List<String> repos
    Map<String, List<Issue>> issueMap
    IndexView(List<Issue> issues) {
        super('index.ftl')
        this.issues = issues
        init()
    }

    String getIssueCount(String repo) {
        return issueMap[repo].size().toString()
    }

    List<Issue> getIssueDetails(String repo) {
        return issueMap[repo]
    }

    private void init() {
        Set<String> uniqRepos = [] as Set
        issueMap = [:]
        for(Issue issue in issues) {
            String name = issue?.repository?.fullName
            if (name != null && issue?.pullRequest?.htmlUrl != null) {
                uniqRepos << name
                if (issueMap[name] == null) {
                    issueMap[name] = [issue]
                } else {
                    issueMap[name] << issue
                }
            }
        }
        repos = uniqRepos.sort()
    }
}
