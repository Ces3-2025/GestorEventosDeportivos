package com.gestoreventosdepor.gestoreventosdeportivos;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {

    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/eventos");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
        else{
            response.getWriter().write("No se pudo obtener el dispatcher");
        }

    }

    public void destroy() {
    }
}