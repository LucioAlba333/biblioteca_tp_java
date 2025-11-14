<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Editorial</title></head>
<body>
<h2>Nueva Editorial</h2>
<form action="editoriales" method="post">
    <input type="hidden" name="action" value="create"/>
    Nombre: <input type="text" name="nombre"/>
    <input type="submit" value="Guardar"/>
</form>
<a href="editoriales?action=list">Volver</a>
</body>
</html>
