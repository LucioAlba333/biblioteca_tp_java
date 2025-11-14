<%@ page import="models.Libro" %>
<%@ page import="models.enums.EstadoEjemplar" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Ejemplar</title></head>
<body>
<h2>Nuevo Ejemplar</h2>
<form action="ejemplares" method="post">
    <input type="hidden" name="action" value="create"/>
    <input type="hidden" name="libroId" value="${libro.id}"/>

    Libro: ${libro.titulo}<br/>

    Estado:
    <select name="estado">
        <%
            for (EstadoEjemplar estado : EstadoEjemplar.values()) {
        %>
        <option value="<%= estado.name() %>"><%= estado.name() %></option>
        <%
            }
        %>
    </select><br/>
    Disponible:
    <select name="disponible">
        <option value="true">Sí</option>
        <option value="false">No</option>
    </select><br/>

    <input type="submit" value="Guardar"/>
</form>
<a href="libros?action=list">Volver a Libros</a>
</body>
</html>
