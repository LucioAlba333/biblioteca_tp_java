<%@ page contentType="text/html;charset=UTF-8"%>
<div style="background-color:#333; padding:10px;">
    <a href="<%= request.getContextPath() %>/autores?action=list" style="color:white;">Autores</a>
    <a href="<%= request.getContextPath() %>/libros?action=list" style="color:white;">Libros</a>
    <a href="<%= request.getContextPath() %>/editoriales?action=list" style="color:white;">Editoriales</a>
    <a href="<%= request.getContextPath() %>/generos?action=list" style="color:white;">Generos</a>
</div>
