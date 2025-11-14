<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Editar Autor</title></head>
<body>
<h2>Editar Autor</h2>
<form action="autores" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${autor.id}"/>
    Nombre: <input type="text" name="nombre" value="${autor.nombre}"/>
    <input type="submit" value="Actualizar"/>
</form>
<a href="autores?action=list">Volver</a>
</body>
</html>