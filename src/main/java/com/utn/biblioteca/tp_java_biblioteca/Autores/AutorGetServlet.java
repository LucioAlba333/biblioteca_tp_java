package com.utn.biblioteca.tp_java_biblioteca.Autores;

import data.AutorRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.Autor;

import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "AutorGetServlet", value = "/AutorGetServlet")
public class AutorGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AutorRepository autorRepository = new AutorRepository();
        LinkedList<Autor> autores = autorRepository.getAllAutores();
        HttpSession session = request.getSession();
        session.setAttribute("autores",autores);
        request.getRequestDispatcher("/WEB-INF/views/listaAutores.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}