package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Prestamo;
import models.Ejemplar;
import models.Persona;
import services.PrestamoService;
import services.EjemplarService;
import services.PersonaService;
import utils.Validacion;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;

@WebServlet("/prestamos")
public class PrestamoServlet extends HttpServlet {
    private PrestamoService prestamoService;
    private EjemplarService ejemplarService;
    private PersonaService personaService;

    @Override
    public void init() throws ServletException {
        prestamoService = new PrestamoService();
        ejemplarService = new EjemplarService();
        personaService = new PersonaService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Prestamo> prestamos = prestamoService.listarPrestamos();
                request.setAttribute("prestamos", prestamos);
                request.getRequestDispatcher("/WEB-INF/views/listarPrestamos.jsp").forward(request, response);
                break;
            case "new":
                request.setAttribute("personas", personaService.listarPersonas());
                request.setAttribute("ejemplares", ejemplarService.listarEjemplares());
                request.getRequestDispatcher("/WEB-INF/views/crearPrestamo.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Prestamo prestamo = null;
                for (Prestamo p : prestamoService.listarPrestamos()) {
                    if (p.getId() == idEdit) {
                        prestamo = p;
                        break;
                    }
                }
                request.setAttribute("prestamo", prestamo);
                request.setAttribute("personas", personaService.listarPersonas());
                request.getRequestDispatcher("/WEB-INF/views/editarPrestamo.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                response.sendRedirect("prestamos?action=list");
                break;
            case "finalizar":
                int idFin = Integer.parseInt(request.getParameter("id"));
                Prestamo prestamoFin = null;
                for (Prestamo p : prestamoService.listarPrestamos()) {
                    if (p.getId() == idFin) {
                        prestamoFin = p;
                        break;
                    }
                }
                if (prestamoFin != null) {
                    prestamoService.finalizarPrestamo(prestamoFin);
                }
                response.sendRedirect("prestamos?action=list");
                break;
            default:
                response.sendRedirect("prestamos?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                int personaId = Integer.parseInt(request.getParameter("personaId"));
                int ejemplarId = Integer.parseInt(request.getParameter("ejemplarId"));
                Date fechaInicio = new Date();
                Date fechaLimite = java.sql.Date.valueOf(request.getParameter("fechaLimite"));

                Persona persona = new Persona();
                persona.setId(personaId);

                Prestamo prestamo = new Prestamo();
                prestamo.setPersona(persona);
                prestamo.setFechaInicio(fechaInicio);
                prestamo.setFechaLimite(fechaLimite);

                Ejemplar ejemplar = ejemplarService.getEjemplarById(ejemplarId);

                Validacion.validar(prestamo);
                prestamoService.crearPrestamo(ejemplar, prestamo);

                response.sendRedirect("prestamos?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int personaId = Integer.parseInt(request.getParameter("personaId"));
                Date fechaInicio = java.sql.Date.valueOf(request.getParameter("fechaInicio"));
                Date fechaLimite = java.sql.Date.valueOf(request.getParameter("fechaLimite"));
                Date fechaDevolucion = request.getParameter("fechaDevolucion") != null
                        ? java.sql.Date.valueOf(request.getParameter("fechaDevolucion"))
                        : null;

                Persona persona = new Persona();
                persona.setId(personaId);

                Prestamo prestamo = new Prestamo();
                prestamo.setId(id);
                prestamo.setPersona(persona);
                prestamo.setFechaInicio(fechaInicio);
                prestamo.setFechaLimite(fechaLimite);
                prestamo.setFechaDevolucion(fechaDevolucion);

                Validacion.validar(prestamo);
                response.sendRedirect("prestamos?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
