package com.example.optionalcoursesfp.photo;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class PhotoManager {
    private final String imageDirectoryPath;
    private static final String USER_AVATARS_DIR = "C:\\Users\\sasha\\apache-tomcat-9.0.58\\usersAvatars";
    private static final String SYSTEM_DIRECTORY_SEPARATOR = "\\";

    public PhotoManager(String imageDirectoryPath) {

        this.imageDirectoryPath = imageDirectoryPath;
    }

    public String getByteStringOfUploadedPhoto(String imageName) throws IOException {
        File file = new File(imageDirectoryPath + SYSTEM_DIRECTORY_SEPARATOR + imageName);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            throw new IOException();
        }finally {
            fis.close();
        }
        byte[] bytes = bos.toByteArray();
        byte[] encodeBase64 = Base64.encodeBase64(bytes);
        return new String(encodeBase64, StandardCharsets.UTF_8);
    }

    public void downLoadPhoto(FileItem item, String imageName) {
        try {
            item.write(new File(USER_AVATARS_DIR, imageName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
