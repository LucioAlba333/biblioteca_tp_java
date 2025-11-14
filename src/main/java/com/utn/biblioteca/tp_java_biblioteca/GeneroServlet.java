package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Genero;
import services.GeneroService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/generos")
public class GeneroServlet extends HttpServlet {
    private GeneroService generoService;

    @Override
    public void init() throws ServletException {
        generoService = new GeneroService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Genero> generos = generoService.listarGeneros();
                request.setAttribute("generos", generos);
                request.getRequestDispatcher("/WEB-INF/views/listarGeneros.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Genero genero = generoService.getGeneroById(idEdit);
                request.setAttribute("genero", genero);
                request.getRequestDispatcher("/WEB-INF/views/editarGenero.jsp").forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher("/WEB-INF/views/crearGenero.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                generoService.eliminarGenero(idDelete);
                response.sendRedirect("generos?action=list");
                break;
            default:
                response.sendRedirect("generos?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                String descripcion = request.getParameter("descripcion");
                Genero nuevo = new Genero();
                nuevo.setDescripcion(descripcion);
                Validacion.validar(nuevo);
                generoService.crearGenero(nuevo);
                response.sendRedirect("generos?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String descripcion = request.getParameter("descripcion");
                Genero genero = new Genero();
                genero.setId(id);
                genero.setDescripcion(descripcion);
                Validacion.validar(genero);
                generoService.editarGenero(genero);
                response.sendRedirect("generos?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
