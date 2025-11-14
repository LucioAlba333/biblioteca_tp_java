<%@ page import="models.Autor" %>
<%@ page import="models.Editorial" %>
<%@ page import="models.Genero" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Libro" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>Editar Libro</title>
    <%Libro libro = ((models.Libro)request.getAttribute("libro"));%>
</head>
<body>
<h2>Editar Libro</h2>

<form action="libros" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="<%=libro.getId()%>"/>

    Título:
    <input type="text" name="titulo" value="<%=libro.getTitulo()%>"/><br/>

    Autor:
    <select name="autorId">
        <%
            List<Autor> autores = (List<Autor>) request.getAttribute("autores");
            int autorId = ((models.Libro) request.getAttribute("libro")).getAutor().getId();
            for (Autor a : autores) {
                String selected = (a.getId() == autorId) ? "selected" : "";
        %>
        <option value="<%= a.getId() %>" <%= selected %>><%= a.getNombre() %></option>
        <%
            }
        %>
    </select><br/>

    Editorial:
    <select name="editorialId">
        <%
            List<Editorial> editoriales = (List<Editorial>) request.getAttribute("editoriales");
            int editorialId = ((models.Libro) request.getAttribute("libro")).getEditorial().getId();
            for (Editorial e : editoriales) {
                String selected = (e.getId() == editorialId) ? "selected" : "";
        %>
        <option value="<%= e.getId() %>" <%= selected %>><%= e.getNombre() %></option>
        <%
            }
        %>
    </select><br/>

    Género:
    <select name="generoId">
        <%
            List<Genero> generos = (List<Genero>) request.getAttribute("generos");
            int generoId = ((models.Libro) request.getAttribute("libro")).getGenero().getId();
            for (Genero g : generos) {
                String selected = (g.getId() == generoId) ? "selected" : "";
        %>
        <option value="<%= g.getId() %>" <%= selected %>><%= g.getDescripcion() %></option>
        <%
            }
        %>
    </select><br/>

    <input type="submit" value="Actualizar"/>
</form>

<a href="libros?action=list">Volver</a>
</body>
</html>
