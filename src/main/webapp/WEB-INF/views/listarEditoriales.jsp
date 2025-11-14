<%@ page import="models.Editorial" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Editoriales</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Editoriales</h2>
<a href="editoriales?action=new">Crear nueva editorial</a>
<table>
    <tr><th>ID</th><th>Nombre</th><th>Acciones</th></tr>
    <%
        List<Editorial> editoriales = (List<Editorial>) request.getAttribute("editoriales");
        if (editoriales != null) {
            for (Editorial editorial : editoriales) {
    %>
    <tr>
        <td><%= editorial.getId() %></td>
        <td><%= editorial.getNombre() %></td>
        <td>
            <a href="editoriales?action=edit&id=<%= editorial.getId() %>">Editar</a>
            <a href="editoriales?action=delete&id=<%= editorial.getId() %>">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
