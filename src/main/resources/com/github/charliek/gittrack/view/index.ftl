<#-- @ftlvariable name="" type="com.github.charliek.gittrack.view.IndexView" -->
<!DOCTYPE html>
<html>
<head>
    <title>Github Pull Requests</title>
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <style>
        body {
        }
        .pullRequests {
            margin: 30px 0;
            padding: 3px;
            background: #eee;
            border-radius: 3px;
        }
        .repo {
            font-weight: bold;
            padding: 8px;
            background: #e6f1f6;
            border: 1px solid #b7c7cf;
            border-top-right-radius: 2px;
            border-top-left-radius: 2px;
        }
        .pullRequests ul {
            padding: 0;
            margin: 0;
        }

        .pullRequests ul li {
            padding: 8px;
            background: #fff;
            border: 1px solid #b7c7cf;
            border-top-width: 0;
            display: block;

            line-height: 20px;
            list-style: none;
            margin: 0;
        }
        span.issueCount {
            float: right;
        }
        span.comment-count {
            float: right;
        }
    </style>
</head>
<body>
<div class="container">
    <#list repos as repo>
        <div class="pullRequests">
            <div class="repo">${repo} <span class="badge badge-inverse issueCount">${getIssueCount(repo)}</span></div>
            <ul>
                <#list getIssueDetails(repo) as issue>
                    <li class="repo-pr">
                        <span class="label author">${issue.user.login}</span>
                        <span class="badge badge-info comment-count">${issue.comments}</span>
                        <a class="gh" href="${issue.htmlUrl}">
                            <span class="desc">${issue.title}</span>
                        </a>
                    </li>
                </#list>
            </ul>
        </div>
    </#list>
</div>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>