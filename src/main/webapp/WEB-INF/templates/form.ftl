<#include "setup.ftl" />
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title}</title>
    <style type="text/css">
        .error { color: red; }
    </style>
</head>
<body>
<h1>${title}</h1>
<form action="<@servletUrl formAction />" method="post">
    <@spring.formHiddenInput "conversation.id" />
    <p>
        <label for="value">Value:</label>
        <@spring.formInput "conversation.value" />
        <@spring.showErrors "<br>" "error" />
    </p>
    <p>
        <input type="submit" value="Submit">
    </p>
</form>
</body>
</html>