package com.hltt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


public class LoginServlet extends HttpServlet {
    private HashMap<String, String> userlist;

    @Override
    public void init() throws ServletException {
        super.init();
        userlist = new HashMap<>();
        userlist.put("admin", "123456");
        userlist.put("tachi1111", "tachisadboy");
        userlist.put("depressedassasin", "kirasuo02");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        resp.setHeader("Content-Type", "text/html");

        PrintWriter out = resp.getWriter();
        if(password == null || username == null){
            out.println("Please enter username and password!");
        }
        else if(username.equals("") || password.equals("")){
            out.println("Username and password shouldn't be blank!");
        }
        else if (username.length() < 5) {
            out.println("Username should have from 5 characters");
        }
        else if (password.length() < 6) {
            out.println("Password should have from 6 characters");
        }
        else if (userlist.containsKey(username) && userlist.get(username).equals(password)){
            out.println("Login successfully");
        }
        else out.println("Wrong username or password");
    }
}
