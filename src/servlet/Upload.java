package servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serial;
import java.util.List;
import database.Database;

public class Upload extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 1024; // 1GB
    private static final long MAX_FILE_SIZE      = 10L * 1024 * 1024 * 1024; // 10GB
    private static final long MAX_REQUEST_SIZE   = 10L * 1024 * 1024 * 1034; // 10GB + 10MB

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int fileId = 0;
        String password = null;

        if (!ServletFileUpload.isMultipartContent(request)) {
            return;
        }

        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setSizeThreshold(MEMORY_THRESHOLD);

        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);


        upload.setFileSizeMax(MAX_FILE_SIZE);


        upload.setSizeMax(MAX_REQUEST_SIZE);


        upload.setHeaderEncoding("UTF-8");

        String dir = request.getServletContext().getRealPath("./disk");

        File fil = new File(dir);
        if (!fil.exists()) {
            fil.mkdir();
        }


        List<FileItem> form;
        try {
            form = upload.parseRequest(request);
        } catch (Exception ex) {
            response.getWriter().print(0);
            return ;
        }

        if (form == null || form.size() <= 0) {
            response.getWriter().print(0);
            return ;
        }


        for (FileItem item : form) {
            if (item.isFormField()) {
                password = item.getString();
            }
        }

        for (FileItem item : form) {
            if (!item.isFormField()) {
                try {
                    Database.set(item.getName(), password);
                } catch (Exception e) {
                    response.getWriter().print(0);
                    return;
                }

                try {
                    fileId = Database.getFileId();
                } catch (Exception e) {
                    response.getWriter().print(0);
                    return ;
                }

                dir = dir + File.separator + fileId;

                fil = new File(dir);
                if (!fil.exists()) {
                    fil.mkdir();
                }

                fil = new File(dir + File.separator + item.getName());
                try {
                    item.write(fil);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.getWriter().print(0);
                    return ;
                }

                response.getWriter().print(fileId);
            }
        }
        return ;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}