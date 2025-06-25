package servlet;

import database.Database;
import database.PairString;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;

import javax.servlet.*;
import javax.servlet.http.*;

public class Download extends HttpServlet {

    private static final long serialVersionUID = 1L;

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String passwordString = request.getParameter("password");
        String fileIdSting = request.getParameter("fileId");

        if (!isInteger(fileIdSting)) {
            response.sendRedirect("/download/error.html");
            return ;
        }

        int fileId = Integer.parseInt(fileIdSting);
        if (fileId <= 0) {
            response.sendRedirect("/download/error.html");
            return ;
        }
        PairString res;
        try {
            res = Database.get(fileId);
        } catch (Exception e) {
            response.sendRedirect("/download/error.html");
            return ;
        }
        if (res.password == null
        || !res.password.equals(passwordString)) {
            response.sendRedirect("/download/error.html");
            return ;
        }
        String path = "/disk/" + fileIdSting + "/";
        String dataDirectory = request.getServletContext().getRealPath(path);
        File file = new File(dataDirectory, res.fileName);
        if (file.exists()) {
            response.setHeader("Content-Length", Long.toString(file.length()));
            response.addHeader("Content-Disposition", "attachment; filename=\"" + java.net.URLEncoder.encode(res.fileName, "UTF-8") + "\"");
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (IOException ex) {
//                System.out.println (ex.toString());
                response.sendRedirect("/download/error.html");
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        } else {
            response.sendRedirect("/download/error.html");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}