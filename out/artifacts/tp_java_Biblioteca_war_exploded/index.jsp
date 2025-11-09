<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="AutorGetServlet">get all autores</a>
<form action="AutorGetByIdServlet" method="get">
    <label for="numero">Ingrese un valor numérico:</label>
    <input type="number" id="autoresId" name="autoresId" required />
    <button type="submit">Enviar</button>
</form>
</body>
</html>