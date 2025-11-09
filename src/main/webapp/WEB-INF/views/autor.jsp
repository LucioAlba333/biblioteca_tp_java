
<%@ page import="models.Autor" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html" charset="UTF-8">
    <title>mostrar autores</title>
</head>
<body>
<%
    Autor autores = (Autor) request.getAttribute("autor");
%>
<p>Id: <%= autores.getId()%></p>
<p>Nombre: <%= autores.getNombre()%></p>
</body>
</html>
