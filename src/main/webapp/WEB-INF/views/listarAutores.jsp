<%@ page import="models.Autor" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Autores</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Autores</h2>
<a href="autores?action=new">Crear nuevo autor</a>
<table>
    <tr><th>ID</th><th>Nombre</th><th>Acciones</th></tr>
    <%
        List<Autor> autores = (List<Autor>) request.getAttribute("autores");
        if (autores != null) {
            for (Autor autor : autores) {
    %>
    <tr>
        <td><%= autor.getId() %></td>
        <td><%= autor.getNombre() %></td>
        <td>
            <a href="autores?action=edit&id=<%= autor.getId() %>">Editar</a>
            <a href="autores?action=delete&id=<%= autor.getId() %>">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
