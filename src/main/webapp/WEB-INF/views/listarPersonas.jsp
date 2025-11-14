<%@ page import="models.Persona" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Personas</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Personas</h2>
<a href="personas?action=new">Crear nueva persona</a>
<table>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Email</th>
        <th>Dirección</th>
        <th>Teléfono</th>
        <th>Acciones</th>
    </tr>
    <%
        List<Persona> personas = (List<Persona>) request.getAttribute("personas");
        if (personas != null) {
            for (Persona p : personas) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getNombre() %></td>
        <td><%= p.getApellido() %></td>
        <td><%= p.getEmail() %></td>
        <td><%= p.getDireccion() %></td>
        <td><%= p.getTelefono() %></td>
        <td>
            <a href="personas?action=edit&id=<%= p.getId() %>">Editar</a>
            <a href="personas?action=delete&id=<%= p.getId() %>">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
