<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h2>Error</h2>
<p><%= request.getAttribute("error") %></p>
</body>
</html>
