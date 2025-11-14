package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Editorial;
import services.EditorialService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/editoriales")
public class EditorialServlet extends HttpServlet {
    private EditorialService editorialService;

    @Override
    public void init() throws ServletException {
        editorialService = new EditorialService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Editorial> editoriales = editorialService.listarEditoriales();
                request.setAttribute("editoriales", editoriales);
                request.getRequestDispatcher("/WEB-INF/views/listarEditoriales.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Editorial editorial = editorialService.getEditorialById(idEdit);
                request.setAttribute("editorial", editorial);
                request.getRequestDispatcher("/WEB-INF/views/editarEditorial.jsp").forward(request, response);
                break;
            case "new":
                request.getRequestDispatcher("/WEB-INF/views/crearEditorial.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                editorialService.eliminarEditorial(idDelete);
                response.sendRedirect("editoriales?action=list");
                break;
            default:
                response.sendRedirect("editoriales?action=list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                String nombre = request.getParameter("nombre");
                Editorial nueva = new Editorial();
                nueva.setNombre(nombre);
                Validacion.validar(nueva);
                editorialService.crearEditorial(nueva);
                response.sendRedirect("editoriales?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String nombre = request.getParameter("nombre");
                Editorial editorial = new Editorial();
                editorial.setId(id);
                editorial.setNombre(nombre);
                Validacion.validar(editorial);
                editorialService.editarEditorial(editorial);
                response.sendRedirect("editoriales?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
