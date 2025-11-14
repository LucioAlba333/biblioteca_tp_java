package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;
import services.AutorService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/autores")
public class AutorServlet extends HttpServlet {
    private AutorService autorService;

    @Override
    public void init() throws ServletException {
        autorService = new AutorService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Autor> autores = autorService.listarAutores();
                request.setAttribute("autores", autores);
                request.getRequestDispatcher("/WEB-INF/views/listarAutores.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Autor autor = autorService.getAutorById(idEdit);
                request.setAttribute("autor", autor);
                request.getRequestDispatcher("/WEB-INF/views/editarAutor.jsp").forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher("/WEB-INF/views/crearAutor.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                autorService.eliminarAutor(idDelete);
                response.sendRedirect("autores?action=list");
                break;
            default:
                response.sendRedirect("autores?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {


        if ("create".equals(action)) {
            String nombre = request.getParameter("nombre");
            Autor nuevo = new Autor();
            nuevo.setNombre(nombre);
            Validacion.validar(nuevo);
            autorService.crearAutor(nuevo);
            response.sendRedirect("autores?action=list");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            Autor autor = new Autor();
            autor.setNombre(nombre);
            autor.setId(id);
            Validacion.validar(autor);
            autorService.editarAutor(autor);
            response.sendRedirect("?action=list");
        }
    }catch(IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}