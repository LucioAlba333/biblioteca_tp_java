package com.utn.biblioteca.tp_java_biblioteca.Autores;

import data.AutorRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;

import java.io.IOException;

@WebServlet(name = "AutorGetByIdServlet", value = "/AutorGetByIdServlet")
public class AutorGetByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorRepository autorRepository = new AutorRepository();
        int id = Integer.parseInt(request.getParameter("autoresId"));
        Autor autor = autorRepository.getAutorById(id);
        if (autor == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("error", "Autor con id " + id + " no encontrado");
            request.getRequestDispatcher("/WEB-INF/views/error404.jsp").forward(request, response);
            return;
        }
        request.setAttribute("autor",autor);
        request.getRequestDispatcher("/WEB-INF/views/autor.jsp").forward(request, response);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}