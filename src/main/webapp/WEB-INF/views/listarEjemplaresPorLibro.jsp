<%@ page import="models.Ejemplar" %>
<%@ page import="models.Libro" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Ejemplares del Libro</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Ejemplares de: ${libro.titulo}</h2>
<a href="ejemplares?action=new&libroId=${libro.id}">Crear nuevo ejemplar</a>
<table>
    <tr><th>ID</th><th>Estado</th><th>Disponible</th><th>Acciones</th></tr>
    <%
        List<Ejemplar> ejemplares = (List<Ejemplar>) request.getAttribute("ejemplares");
        if (ejemplares != null) {
            for (Ejemplar e : ejemplares) {
    %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getEstado() %></td>
        <td><%= e.isDisponible() ? "Sí" : "No" %></td>
        <td>
            <a href="ejemplares?action=edit&id=<%= e.getId() %>&libroId=<%= e.getLibro().getId() %>">Editar</a>
            <a href="ejemplares?action=delete&id=<%= e.getId() %>&libroId=<%= e.getLibro().getId() %>">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<a href="libros?action=list">Volver a Libros</a>
</body>
</html>
