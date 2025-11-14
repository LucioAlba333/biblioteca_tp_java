<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Crear Persona</title></head>
<body>
<h2>Nueva Persona</h2>
<form action="personas" method="post">
    <input type="hidden" name="action" value="create"/>
    Nombre: <input type="text" name="nombre"/><br/>
    Apellido: <input type="text" name="apellido"/><br/>
    Email: <input type="text" name="email"/><br/>
    Dirección: <input type="text" name="direccion"/><br/>
    Teléfono: <input type="text" name="telefono"/><br/>
    <input type="submit" value="Guardar"/>
</form>
<a href="personas?action=list">Volver</a>
</body>
</html>
