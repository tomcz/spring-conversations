<#include "setup.ftl" />
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Success</title>
</head>
<body>
<h1>
    Created object with email address: ${object.email?html}
</h1>
<p>
<#list formLinks?keys as key>
    <a href="<@servletUrl formLinks[key] />">${key?html}</a><br>
</#list>
    <a href="<@spring.url '/' />">Home</a>
</p>
</body>
</html>