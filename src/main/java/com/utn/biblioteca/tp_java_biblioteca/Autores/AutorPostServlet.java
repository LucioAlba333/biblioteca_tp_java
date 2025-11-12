package com.utn.biblioteca.tp_java_biblioteca.Autores;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;
import services.AutorService;
import utils.Validacion;

import java.io.IOException;

@WebServlet(name = "AutorPostServlet", value = "/AutorPostServlet")
public class AutorPostServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorService autorService = new AutorService();
        try {
            Autor autor = new Autor();
            autor.setNombre("");
            Validacion.validar(autor);
            autorService.crearAutor(autor);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }catch(IllegalArgumentException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("statusCode", HttpServletResponse.SC_BAD_REQUEST);
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
        }


    }
}