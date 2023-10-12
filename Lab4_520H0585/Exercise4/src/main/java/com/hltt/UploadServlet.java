package com.hltt;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UploadServlet", value = "/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private List<String> supportedExtension;
    @Override
    public void init() throws ServletException {
        super.init();
        supportedExtension = new ArrayList<>();
        supportedExtension.add("txt");
        supportedExtension.add("doc");
        supportedExtension.add("docx");
        supportedExtension.add("img");
        supportedExtension.add("pdf");
        supportedExtension.add("rar");
        supportedExtension.add("zip");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("This web does not support Get method");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/html");

        String name = req.getParameter("name");
        String override = req.getParameter("override");
        Part part = req.getPart("file");
        if (part == null){
            resp.getWriter().println("Please provide a file");
            return;
        }

        String uploadPath = getServletContext().getRealPath("uploads"); //get the root directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()){
            uploadDir.mkdir();
        }

        String destination = "";
        if (name == null || name.isEmpty()){
            destination = uploadPath + File.separator + part.getSubmittedFileName();
        }
        else destination = uploadPath + File.separator + name;

        File filecheck = new File(destination);
        if(filecheck.exists()){
            if(override == null){
                resp.getWriter().println("File already exists");
            }
            else {
                String fileName = filecheck.getName();
                int index = fileName.lastIndexOf('.');
                String extension = "";
                if (index > 0) {
                    extension = fileName.substring(index+1);
                }

                if(supportedExtension.contains(extension)){
                    part.write(destination);
                    resp.getWriter().println("File has been overriden<br>");
                    resp.getWriter().println("File uploaded. Click <a href=\"#\">here</a> to visit file");
                }
                else{
                    resp.getWriter().println("Unsupported file extension");
                }
            }
        }
        else{
            String fileName = filecheck.getName();
            int index = fileName.lastIndexOf('.');
            String extension = "";
            if (index > 0) {
                extension = fileName.substring(index+1);
            }

            if(supportedExtension.contains(extension)){
                part.write(destination);
                resp.getWriter().println("File uploaded. Click <a href=\"#\">here</a> to visit file");
            }
            else{
                resp.getWriter().println("Unsupported file extension");
            }
        }
    }
}
