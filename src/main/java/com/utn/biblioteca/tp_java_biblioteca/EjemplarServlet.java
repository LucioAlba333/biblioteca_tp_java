package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Ejemplar;
import models.Libro;
import models.enums.EstadoEjemplar;
import services.EjemplarService;
import services.LibroService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/ejemplares")
public class EjemplarServlet extends HttpServlet {
    private EjemplarService ejemplarService;
    private LibroService libroService;

    @Override
    public void init() throws ServletException {
        ejemplarService = new EjemplarService();
        libroService = new LibroService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Ejemplar> ejemplares = ejemplarService.listarEjemplares();
                request.setAttribute("ejemplares", ejemplares);
                request.getRequestDispatcher("/WEB-INF/views/listarEjemplares.jsp").forward(request, response);
                break;
            case "viewByLibro":
                int libroId = Integer.parseInt(request.getParameter("libroId"));
                LinkedList<Ejemplar> ejemplaresPorLibro = ejemplarService.listarEjemplaresPorLibro(libroId);
                request.setAttribute("ejemplares", ejemplaresPorLibro);
                request.setAttribute("libro", libroService.getLibroById(libroId));
                request.getRequestDispatcher("/WEB-INF/views/listarEjemplaresPorLibro.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Ejemplar ejemplar = ejemplarService.getEjemplarById(idEdit);
                request.setAttribute("ejemplar", ejemplar);
                request.setAttribute("estados", EstadoEjemplar.values());
                request.getRequestDispatcher("/WEB-INF/views/editarEjemplar.jsp").forward(request, response);
                break;
            case "new":
                int libroIdNew = Integer.parseInt(request.getParameter("libroId"));
                Libro libro = libroService.getLibroById(libroIdNew);
                request.setAttribute("libro", libro);
                request.setAttribute("estados", EstadoEjemplar.values());
                request.getRequestDispatcher("/WEB-INF/views/crearEjemplar.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                ejemplarService.eliminarEjemplar(idDelete);
                response.sendRedirect("ejemplares?action=list");
                break;
            default:
                response.sendRedirect("ejemplares?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                int libroId = Integer.parseInt(request.getParameter("libroId"));
                EstadoEjemplar estado = EstadoEjemplar.valueOf(request.getParameter("estado"));
                boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));

                Ejemplar nuevo = new Ejemplar();
                Libro libro = new Libro();
                libro.setId(libroId);
                nuevo.setLibro(libro);
                nuevo.setEstado(estado);
                nuevo.setDisponible(disponible);

                Validacion.validar(nuevo);
                ejemplarService.crearEjemplar(nuevo);
                response.sendRedirect("libros?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int libroId = Integer.parseInt(request.getParameter("libroId"));
                EstadoEjemplar estado = EstadoEjemplar.valueOf(request.getParameter("estado"));
                boolean disponible = Boolean.parseBoolean(request.getParameter("disponible"));

                Ejemplar ejemplar = new Ejemplar();
                ejemplar.setId(id);
                Libro libro = new Libro();
                libro.setId(libroId);
                ejemplar.setLibro(libro);
                ejemplar.setEstado(estado);
                ejemplar.setDisponible(disponible);

                Validacion.validar(ejemplar);
                ejemplarService.editarEjemplar(ejemplar);
                response.sendRedirect("ejemplares?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
