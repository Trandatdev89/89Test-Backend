package com.project01.quiz.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class UploadFile {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFiles(MultipartFile file) {
        String fileName=file.getOriginalFilename().replace(" ", "_");
        try {
            File uploadFile=new File(uploadDir);
            if(!uploadFile.exists()){
                uploadFile.mkdirs();
            }
            FileCopyUtils.copy(file.getBytes(),new File(uploadDir+fileName));
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileName;
    }
}
