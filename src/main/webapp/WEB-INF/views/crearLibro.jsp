<%@ page import="models.Autor" %>
<%@ page import="models.Editorial" %>
<%@ page import="models.Genero" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Libro</title></head>
<body>
<h2>Nuevo Libro</h2>
<form action="libros" method="post">
    <input type="hidden" name="action" value="create"/>
    Título: <input type="text" name="titulo"/><br/>

    Autor:
    <select name="autorId">
        <%
            List<Autor> autores = (List<Autor>) request.getAttribute("autores");
            for (Autor a : autores) {
        %>
        <option value="<%= a.getId() %>"><%= a.getNombre() %></option>
        <%
            }
        %>
    </select><br/>

    Editorial:
    <select name="editorialId">
        <%
            List<Editorial> editoriales = (List<Editorial>) request.getAttribute("editoriales");
            for (Editorial e : editoriales) {
        %>
        <option value="<%= e.getId() %>"><%= e.getNombre() %></option>
        <%
            }
        %>
    </select><br/>

    Género:
    <select name="generoId">
        <%
            List<Genero> generos = (List<Genero>) request.getAttribute("generos");
            for (Genero g : generos) {
        %>
        <option value="<%= g.getId() %>"><%= g.getDescripcion() %></option>
        <%
            }
        %>
    </select><br/>

    <input type="submit" value="Guardar"/>
</form>
<a href="libros?action=list">Volver</a>
</body>
</html>
