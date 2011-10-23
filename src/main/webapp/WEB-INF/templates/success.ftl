<#include "setup.ftl" />
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Success</title>
    <link rel="stylesheet" type="text/css" href="<@spring.url '/static/css/main.css' />">
</head>
<body>
<div id="container">
    <h1>
        Created object with email address: ${object.email?html}
    </h1>
    <p>
        <a href="<@spring.url '/' />">Home</a>
    </p>
</div>
</body>
</html>