package com.utn.biblioteca.tp_java_biblioteca;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;
import models.Editorial;
import models.Genero;
import models.Libro;
import services.AutorService;
import services.EditorialService;
import services.GeneroService;
import services.LibroService;
import utils.Validacion;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet("/libros")
public class LibroServlet extends HttpServlet {
    private LibroService libroService;

    @Override
    public void init() throws ServletException {
        libroService = new LibroService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                LinkedList<Libro> libros = libroService.listarLibros();
                request.setAttribute("libros", libros);
                request.getRequestDispatcher("/WEB-INF/views/listarLibros.jsp").forward(request, response);
                break;
            case "edit":
                int idEdit = Integer.parseInt(request.getParameter("id"));
                Libro libro = libroService.getLibroById(idEdit);
                request
                        .setAttribute("libro", libro);
                request
                        .setAttribute("autores", new AutorService().listarAutores());
                request
                        .setAttribute("editoriales", new EditorialService().listarEditoriales());
                request
                        .setAttribute("generos", new GeneroService().listarGeneros());
                request.getRequestDispatcher("/WEB-INF/views/editarLibro.jsp").forward(request, response);
                break;
            case "new":
                request.setAttribute("autores", new AutorService().listarAutores());
                request.setAttribute("editoriales", new EditorialService().listarEditoriales());
                request.setAttribute("generos", new GeneroService().listarGeneros());
                request.getRequestDispatcher("/WEB-INF/views/crearLibro.jsp").forward(request, response);
                break;
            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                libroService.eliminarLibro(idDelete);
                response.sendRedirect("libros?action=list");
                break;
            default:
                response.sendRedirect("libros?action=list");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if ("create".equals(action)) {
                String titulo = request.getParameter("titulo");
                int autorId = Integer.parseInt(request.getParameter("autorId"));
                int editorialId = Integer.parseInt(request.getParameter("editorialId"));
                int generoId = Integer.parseInt(request.getParameter("generoId"));

                Libro nuevo = new Libro();
                nuevo.setTitulo(titulo);

                Autor autor = new Autor();
                autor.setId(autorId);
                nuevo.setAutor(autor);
                Editorial editorial = new Editorial();
                editorial.setId(editorialId);
                nuevo.setEditorial(editorial);
                Genero genero = new Genero();
                genero.setId(generoId);
                nuevo.setGenero(genero);

                Validacion.validar(nuevo);
                libroService.crearLibro(nuevo);
                response.sendRedirect("libros?action=list");
            } else if ("update".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String titulo = request.getParameter("titulo");
                int autorId = Integer.parseInt(request.getParameter("autorId"));
                int editorialId = Integer.parseInt(request.getParameter("editorialId"));
                int generoId = Integer.parseInt(request.getParameter("generoId"));

                Libro libro = new Libro();
                libro.setTitulo(titulo);
                libro.setId(id);
                Autor autor = new Autor();
                autor.setId(autorId);
                libro.setAutor(autor);
                Editorial editorial = new Editorial();
                editorial.setId(editorialId);
                libro.setEditorial(editorial);
                Genero genero = new Genero();
                genero.setId(generoId);
                libro.setGenero(genero);
                Validacion.validar(libro);
                libroService.editarLibro(libro);
                response.sendRedirect("libros?action=list");
            }
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }
    }
}
