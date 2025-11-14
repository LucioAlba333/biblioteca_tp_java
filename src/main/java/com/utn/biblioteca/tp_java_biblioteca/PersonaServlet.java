package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Persona;
import services.PersonaService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/personas")
public class PersonaServlet extends HttpServlet {
    private PersonaService personaService;

    @Override
    public void init() throws ServletException {
        personaService = new PersonaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Persona> personas = personaService.listarPersonas();
                request.setAttribute("personas", personas);
                request.getRequestDispatcher("/WEB-INF/views/listarPersonas.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Persona persona = personaService.getPersonaById(idEdit);
                request.setAttribute("persona", persona);
                request.getRequestDispatcher("/WEB-INF/views/editarPersona.jsp").forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher("/WEB-INF/views/crearPersona.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                personaService.eliminarPersona(idDelete);
                response.sendRedirect("personas?action=list");
                break;
            default:
                response.sendRedirect("personas?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String email = request.getParameter("email");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");

                Persona nueva = new Persona();
                nueva.setNombre(nombre);
                nueva.setApellido(apellido);
                nueva.setEmail(email);
                nueva.setDireccion(direccion);
                nueva.setTelefono(telefono);

                Validacion.validar(nueva);
                personaService.crearPersona(nueva);
                response.sendRedirect("personas?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                String apellido = request.getParameter("apellido");
                String email = request.getParameter("email");
                String direccion = request.getParameter("direccion");
                String telefono = request.getParameter("telefono");

                Persona persona = new Persona();
                persona.setId(id);
                persona.setNombre(nombre);
                persona.setApellido(apellido);
                persona.setEmail(email);
                persona.setDireccion(direccion);
                persona.setTelefono(telefono);

                Validacion.validar(persona);
                personaService.editarPersona(persona);
                response.sendRedirect("personas?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
