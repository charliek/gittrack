package com.github.charliek.gittrack.view

import com.github.charliek.gittrack.transfer.Issue
import com.github.charliek.gittrack.transfer.PullRequest
import com.github.charliek.gittrack.transfer.Repository
import spock.lang.Specification

class IndexViewSpec extends Specification {

    def 'test basic initializing of data structure'() {
        given:
        List<Issue> issues = []
        Issue bcdIssue = new Issue(
                repository: new Repository(fullName: 'bcd'),
                pullRequest: new PullRequest(htmlUrl: 'not null')
        )
        issues << bcdIssue

        // Issues with no pull requests should be ignored
        issues << new Issue(
                repository: new Repository(fullName: 'abc')
        )
        Issue abcIssue = new Issue(
                repository: new Repository(fullName: 'abc'),
                pullRequest: new PullRequest(htmlUrl: 'not null')
        )
        issues << abcIssue

                when:
        IndexView indexView = new IndexView(issues)

        then:
        assert indexView.repos == ['abc', 'bcd']
        assert indexView.issueMap.size() == 2
        assert indexView.issueMap['abc'][0].is(abcIssue)
        assert indexView.issueMap['bcd'][0].is(bcdIssue)
        assert indexView.getIssueCount('abc') == '1'
        assert indexView.getIssueDetails('bcd')[0].is(bcdIssue)
    }
}
