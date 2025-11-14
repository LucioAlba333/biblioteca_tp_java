<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Editar Editorial</title></head>
<body>
<h2>Editar Editorial</h2>
<form action="editoriales" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${editorial.id}"/>
    Nombre: <input type="text" name="nombre" value="${editorial.nombre}"/>
    <input type="submit" value="Actualizar"/>
</form>
<a href="editoriales?action=list">Volver</a>
</body>
</html>
