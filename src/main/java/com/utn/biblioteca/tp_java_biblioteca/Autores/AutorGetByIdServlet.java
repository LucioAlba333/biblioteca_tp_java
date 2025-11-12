package com.utn.biblioteca.tp_java_biblioteca.Autores;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;
import services.AutorService;

import java.io.IOException;

@WebServlet(name = "AutorGetByIdServlet", value = "/AutorGetByIdServlet")
public class AutorGetByIdServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorService autorService = new AutorService();
        int id = Integer.parseInt(request.getParameter("autoresId"));
        Autor autor = autorService.getAutorById(id);
        if (autor == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            request.setAttribute("error", "Autor con id " + id + " no encontrado");
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("autor",autor);
        request.getRequestDispatcher("/WEB-INF/views/autor.jsp").forward(request, response);


    }


}