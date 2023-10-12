package com.hltt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "ImageServlet1", value = "/image1")
public class ImageServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("Sui.png");
        OutputStream outputStream = resp.getOutputStream();

        resp.setHeader("Content-Type", "image/png");

        byte[] buffer = new byte[1024];
        int count;

        while((count = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, count);
        }

        inputStream.close();
        outputStream.close();
    }
}
