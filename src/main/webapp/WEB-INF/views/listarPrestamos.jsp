<%@ page import="models.Prestamo" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Lista de Préstamos</title></head>
<body>
<%@ include file="../../navbar.jsp" %>
<h2>Préstamos</h2>
<a href="prestamos?action=new">Crear nuevo préstamo</a>
<table>
    <tr>
        <th>ID</th>
        <th>Persona</th>
        <th>Fecha Inicio</th>
        <th>Fecha Límite</th>
        <th>Fecha Devolución</th>
        <th>Ejemplares</th>
        <th>Acciones</th>
    </tr>
    <%
        List<Prestamo> prestamos = (List<Prestamo>) request.getAttribute("prestamos");
        if (prestamos != null) {
            for (Prestamo p : prestamos) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getPersona().getNombreCompleto()%></td>
        <td><%= p.getFechaInicio() %></td>
        <td><%= p.getFechaLimite() %></td>
        <td><%= p.getFechaDevolucion() != null ? p.getFechaDevolucion() : "-" %></td>
        <td>
            <%
                for (models.Ejemplar e : p.ejemplares) {
            %>
        Ejemplar ID: <%=e.getId()%><br/>
        <%
            }
            %>
        </td>
        <td>
            <a href="prestamos?action=edit&id=<%= p.getId() %>">Editar</a>
            <a href="prestamos?action=finalizar&id=<%= p.getId() %>">Finalizar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
