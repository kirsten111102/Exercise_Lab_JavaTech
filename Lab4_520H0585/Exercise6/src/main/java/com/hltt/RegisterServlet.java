package com.hltt;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Year;


//http://web-tdtu.herokuapp.com/lab01/register.php
@WebServlet(name = "RegisterServlet", value = "/results")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html");

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String birthday = req.getParameter("birthday");
        String birthtime = req.getParameter("birthtime");
        String gender = req.getParameter("gender");
        String country = req.getParameter("country");
        String[] favorite_ide = req.getParameterValues("favorite_ide[]");
        String toeicScore = req.getParameter("toeic");
        String message = req.getParameter("message");

        if(nameValidation(name) == 1){
            resp.getWriter().println("Please enter your name");
            return;
        } else if (nameValidation(name) == 2) {
            resp.getWriter().println("Your name must have more than 5 characters");
            return;
        }

        if(emailValidation(email) == 1){
            resp.getWriter().println("Please enter your email");
            return;
        } else if (emailValidation(email) == 2) {
            resp.getWriter().println("Your email must have more than 6 characters");
            return;
        } else if (emailValidation(email) == 3) {
            resp.getWriter().println("Invalid form of email");
            return;
        }

        if(birthdayValidation(birthday) == 1){
            resp.getWriter().println("Please enter your birthday");
            return;
        } else if (birthdayValidation(birthday) == 2) {
            resp.getWriter().println("You should be more than 11 years old");
            return;
        }

        if(birthtimeValidation(birthtime) == 1){
            resp.getWriter().println("Please enter your birthtime");
            return;
        }

        if(genderValidation(gender) == 1){
            resp.getWriter().println("Please select your gender");
            return;
        }

        if(countryValidation(country) == 1){
            resp.getWriter().println("Please select your country");
            return;
        }

        if(messageValidation(message) == 1){
            resp.getWriter().println("Please enter your message");
            return;
        }

        resp.getWriter().println(
                "Name: " + name + "<br>" +
                "Email: " + email + "<br>" +
                "Birthday: " + birthday + "<br>" +
                "Birthtime: " + birthtime + "<br>" +
                "Gender: " + gender + "<br>" +
                "Country: " + country + "<br>" +
                "TOEIC Score: " + toeicScore + "<br>" +
                "Favorite IDE: "
        );
        if(IDEchecked(favorite_ide) == 1){
            resp.getWriter().println("None<br>");
        }
        else{
            resp.getWriter().println("<ul>");
            for (String ide : favorite_ide) {
                resp.getWriter().println("<li>" + ide + "</li>");
            }
            resp.getWriter().println("</ul>");
        }
        resp.getWriter().println("Message: " + message);
    }

    private int nameValidation(String name){
        if(name.isEmpty()) return 1;
        else if(name.length() <= 5) return 2;
        else return 0;
    }

    private int emailValidation(String email){
        if(email.isEmpty()) return 1;
        else if(email.length() <= 6) return 2;
        else if(!email.contains("@")) return 3;
        else return 0;
    }

    private int birthdayValidation(String birthday){
        if(birthday.isEmpty()) return 1;
        else{
            String[] dateparts = birthday.split("-");
            if(Integer.parseInt(String.valueOf(Year.now())) - Integer.parseInt(dateparts[0]) < 12) return 2;
            else return 0;
        }
    }

    private int birthtimeValidation(String birthtime){
        if(birthtime.isEmpty()) return 1;
        else return 0;
    }

    private int genderValidation(String gender){
        if(gender.isEmpty()) return 1;
        else return 0;
    }

    private int countryValidation(String country){
        if(country.isEmpty()) return 1;
        else return 0;
    }

    private int IDEchecked(String[] favorite_ide){
        if(favorite_ide == null) return 1;
        else return 0;
    }

    private int messageValidation(String message){
        if(message.isEmpty()) return 1;
        else return 0;
    }
}
