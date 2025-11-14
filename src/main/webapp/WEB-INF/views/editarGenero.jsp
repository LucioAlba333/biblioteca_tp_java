<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Editar Genero</title></head>
<body>
<h2>Editar Genero</h2>
<form action="generos" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${genero.id}"/>
    Descripcion: <input type="text" name="descripcion" value="${genero.descripcion}"/>
    <input type="submit" value="Actualizar"/>
</form>
<a href="generos?action=list">Volver</a>
</body>
</html>