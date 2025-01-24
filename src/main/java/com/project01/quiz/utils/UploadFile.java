package com.project01.quiz.utils;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public class UploadFile {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFiles(MultipartFile file){
        try {
            return cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
