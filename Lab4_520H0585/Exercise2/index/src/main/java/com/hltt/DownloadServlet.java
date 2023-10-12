package com.hltt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(name = "DownloadServlet", value = "/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("file");
        String speedvalue = req.getParameter("speed");
        int speed = 0;

        if(fileName == null || fileName.equals("")){
            resp.getWriter().println("Invalid file name");
            return;
        }

        if(speedvalue != null){
            if(speedvalue.equals("") || speedvalue.equals("0")){
                speed = 1;
            }
            else speed = Integer.parseInt(speedvalue);
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if(inputStream == null){
            resp.getWriter().println("File not found");
            return;
        }
        OutputStream outputStream = resp.getOutputStream();

        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        byte[] buffer = new byte[1024];
        int count;

        while((count = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, count);
            if(speedvalue != null) {
                try {
                    Thread.sleep(Math.round(1024 / speed));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        inputStream.close();
        outputStream.close();
    }
}
