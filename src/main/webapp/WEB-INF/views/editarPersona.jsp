<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Editar Persona</title></head>
<body>
<h2>Editar Persona</h2>
<form action="personas" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="id" value="${persona.id}"/>
    Nombre: <input type="text" name="nombre" value="${persona.nombre}"/><br/>
    Apellido: <input type="text" name="apellido" value="${persona.apellido}"/><br/>
    Email: <input type="text" name="email" value="${persona.email}"/><br/>
    Dirección: <input type="text" name="direccion" value="${persona.direccion}"/><br/>
    Teléfono: <input type="text" name="telefono" value="${persona.telefono}"/><br/>
    <input type="submit" value="Actualizar"/>
</form>
<a href="personas?action=list">Volver</a>
</body>
</html>
