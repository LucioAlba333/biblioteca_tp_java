<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%String error = request.getAttribute("error").toString();%>
    <%String statusCode = request.getAttribute("statusCode").toString();%>
    <title>Error</title>
</head>
<body>
<%@ include file="../../navbar.jsp" %>
<h1>Status <%=statusCode%></h1>
<h2>Error</h2>
<p><%=error%></p>
</body>
</html>
