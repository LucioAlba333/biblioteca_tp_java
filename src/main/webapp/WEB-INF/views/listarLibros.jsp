<%@ page import="models.Libro" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Libros</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Libros</h2>
<a href="libros?action=new">Crear nuevo libro</a>
<table>
    <tr>
        <th>ID</th>
        <th>Título</th>
        <th>Autor</th>
        <th>Editorial</th>
        <th>Género</th>
        <th>Nro de ejemplares</th>
        <th>Acciones</th>
    </tr>
    <%
        List<Libro> libros = (List<Libro>) request.getAttribute("libros");
        if (libros != null) {
            for (Libro libro : libros) {
    %>
    <tr>
        <td><%= libro.getId() %></td>
        <td><%= libro.getTitulo() %></td>
        <td><%= libro.getAutor().getNombre() %></td>
        <td><%= libro.getEditorial().getNombre() %></td>
        <td><%= libro.getGenero().getDescripcion() %></td>
        <td><%= libro.ejemplares.size()%></td>
        <td>
            <a href="libros?action=edit&id=<%= libro.getId() %>">Editar</a>
            <a href="libros?action=delete&id=<%= libro.getId() %>">Eliminar</a>
            <a href="ejemplares?action=new&libroId=<%= libro.getId() %>">Crear Ejemplar</a>
            <a href="ejemplares?action=viewByLibro&libroId=<%= libro.getId() %>">Ver Ejemplares</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
