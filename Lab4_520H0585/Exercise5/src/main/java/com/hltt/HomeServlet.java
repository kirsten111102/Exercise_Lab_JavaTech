package com.hltt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pagename = req.getParameter("page");

        if(pagename == null || (!pagename.equals("about") && !pagename.equals("contact") && !pagename.equals("help"))){
            resp.setHeader("Content-Type", "text/html");
            resp.getWriter().println("<h1>Welcome to JSP Servlet</h1>");
        } else if (pagename.equals("about")) {
            //resp.sendRedirect("about.jsp");
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("about.jsp");
            requestDispatcher.forward(req, resp);
        } else if (pagename.equals("contact")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("contact.jsp");
            requestDispatcher.forward(req, resp);
        } else if (pagename.equals("help")) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("help.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
