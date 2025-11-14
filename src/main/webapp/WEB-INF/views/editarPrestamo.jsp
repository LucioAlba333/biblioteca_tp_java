<%@ page import="models.Persona" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Editar Préstamo</title></head>
<body>
<h2>Editar Préstamo</h2>
<form action="prestamos" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${prestamo.id}"/>

    Persona:
    <select name="personaId">
        <%
            List<Persona> personas = (List<Persona>) request.getAttribute("personas");
            int personaId = ((models.Prestamo)request.getAttribute("prestamo")).getPersona().getId();
            for (Persona persona : personas) {
                String selected = (persona.getId() == personaId) ? "selected" : "";
        %>
        <option value="<%= persona.getId() %>" <%= selected %>><%= persona.getNombre() %> <%= persona.getApellido() %></option>
        <%
            }
        %>
    </select><br/>

    Fecha inicio: <input type="date" name="fechaInicio" value="${prestamo.fechaInicio}"/><br/>
    Fecha límite: <input type="date" name="fechaLimite" value="${prestamo.fechaLimite}"/><br/>
    Fecha devolución: <input type="date" name="fechaDevolucion" value="${prestamo.fechaDevolucion}"/><br/>

    <input type="submit" value="Actualizar"/>
</form>
<a href="prestamos?action=list">Volver</a>
</body>
</html>
