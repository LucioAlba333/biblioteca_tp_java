<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Genero</title></head>
<body>
<h2>Nuevo Genero</h2>
<form action="generos" method="post">
    <input type="hidden" name="action" value="create"/>
    Descripcion: <input type="text" name="descripcion"/>
    <input type="submit" value="Guardar"/>
</form>
<a href="generos?action=list">Volver</a>
</body>
</html>