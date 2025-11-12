package com.utn.biblioteca.tp_java_biblioteca.Autores;


import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;
import services.AutorService;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "AutorGetServlet", value = "/AutorGetServlet")
public class AutorGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorService autorService = new AutorService();
        LinkedList<Autor> autores = autorService.listarAutores();
        HttpSession session = request.getSession();
        session.setAttribute("autores",autores);
        request.getRequestDispatcher("/WEB-INF/views/listaAutores.jsp").forward(request, response);
    }


}