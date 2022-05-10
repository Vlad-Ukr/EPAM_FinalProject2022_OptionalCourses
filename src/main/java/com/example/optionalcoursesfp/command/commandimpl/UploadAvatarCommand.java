package com.example.optionalcoursesfp.command.commandimpl;

import com.example.optionalcoursesfp.command.Command;
import com.example.optionalcoursesfp.entity.User;
import com.example.optionalcoursesfp.exeption.DatabaseException;
import com.example.optionalcoursesfp.photo.PhotoManager;
import com.example.optionalcoursesfp.service.CourseService;
import com.example.optionalcoursesfp.service.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class UploadAvatarCommand implements Command {
    private final UserService userService;
    private final CourseService courseService;

    public UploadAvatarCommand(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @Override
    public void executeCommand(HttpServletRequest request, HttpServletResponse response) {
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = (List<FileItem>) upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    String type = item.getContentType().split("/")[0];
                    if (!type.equals("image")) {
                        request.setAttribute("deniedMessage", "Pls");
                        new HomeCommand(courseService).executeCommand(request, response);
                        return;
                    }
                    User user = (User) request.getSession().getAttribute("user");
                    String imgName = user.getId() + ".png";
                    user.setAvatarImageName(imgName);
                    PhotoManager photoManager = new PhotoManager("C:\\Users\\sasha\\apache-tomcat-9.0.58\\usersAvatars");
                    photoManager.downLoadPhoto(item, imgName);
                    request.getSession().setAttribute("base64Encoded", photoManager.getByteStringOfUploadedPhoto(imgName));
                    userService.changeAvatar(user.getLogin(), imgName);
                    request.getSession().setAttribute("user", user);
                }
            }
            new HomeCommand(courseService).executeCommand(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DatabaseException | FileUploadException throwables) {
            throwables.printStackTrace();
            try {
                request.getRequestDispatcher("Error.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }

        }
    }
}

