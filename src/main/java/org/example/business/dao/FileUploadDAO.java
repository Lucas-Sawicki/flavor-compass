package org.example.business.dao;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadDAO {

    String uploadFile(MultipartFile multipartFile) throws IOException;

}
