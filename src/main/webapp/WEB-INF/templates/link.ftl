<#include "setup.ftl" />
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Link to Form</title>
</head>
<body>
<p>
    <a href="<@spring.url "/notabs/form" />">old school</a>
</p>
<p>
    <a href="<@servletUrl formLink />">new hotness</a>
</p>
</body>
</html>