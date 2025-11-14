<%@ page import="models.Persona" %>
<%@ page import="models.Ejemplar" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Préstamo</title></head>
<body>
<h2>Nuevo Préstamo</h2>
<form action="prestamos" method="post">
    <input type="hidden" name="action" value="create"/>

    Persona:
    <select name="personaId">
        <%
            List<Persona> personas = (List<Persona>) request.getAttribute("personas");
            for (Persona persona : personas) {
        %>
        <option value="<%= persona.getId() %>"><%= persona.getNombre() %> <%= persona.getApellido() %></option>
        <%
            }
        %>
    </select><br/>

    Ejemplar:
    <select name="ejemplarId">
        <%
            List<Ejemplar> ejemplares = (List<Ejemplar>) request.getAttribute("ejemplares");
            for (Ejemplar e : ejemplares) {
                if (e.isDisponible()) {
        %>
        <option value="<%= e.getId() %>">
            Ejemplar ID: <%= e.getId() %> - Libro: <%= e.getLibro().getTitulo() %>
        </option>
        <%
                }
            }
        %>
    </select><br/>


    Fecha límite: <input type="date" name="fechaLimite"/><br/>

    <input type="submit" value="Guardar"/>
</form>
<a href="prestamos?action=list">Volver</a>
</body>
</html>
