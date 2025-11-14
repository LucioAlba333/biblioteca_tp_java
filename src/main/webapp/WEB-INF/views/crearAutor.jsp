<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Autor</title></head>
<body>
<h2>Nuevo Autor</h2>
<form action="autores" method="post">
    <input type="hidden" name="action" value="create"/>
    Nombre: <input type="text" name="nombre"/>
    <input type="submit" value="Guardar"/>
</form>
<a href="autores?action=list">Volver</a>
</body>
</html>