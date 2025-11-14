<%@ page import="models.Genero" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Géneros</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Géneros</h2>
<a href="generos?action=new">Crear nuevo género</a>
<table>
    <tr><th>ID</th><th>Descripción</th><th>Acciones</th></tr>
    <%
        List<Genero> generos = (List<Genero>) request.getAttribute("generos");
        if (generos != null) {
            for (Genero genero : generos) {
    %>
    <tr>
        <td><%= genero.getId() %></td>
        <td><%= genero.getDescripcion() %></td>
        <td>
            <a href="generos?action=edit&id=<%= genero.getId() %>">Editar</a>
            <a href="generos?action=delete&id=<%= genero.getId() %>">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
