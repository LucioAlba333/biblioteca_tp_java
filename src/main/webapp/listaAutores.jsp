<%@ page import="java.util.LinkedList" %>
<%@ page import="models.Autor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html" charset="UTF-8">
        <title>mostrar autores</title>
    </head>
    <body>
        <%
            LinkedList<Autor> autores = (LinkedList<Autor>) request.getSession().getAttribute("autores");
            for(Autor a : autores){
        %>
        <p>Id: <%= a.getId()%></p>
        <p>Nombre: <%= a.getNombre()%></p>
    <%} %>
    </body>
</html>
