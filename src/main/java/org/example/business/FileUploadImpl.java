package org.example.business;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.example.business.dao.FileUploadDAO;
import org.example.domain.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUploadDAO {

    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        try {
            return cloudinary.uploader()
                    .upload(multipartFile.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString();
        } catch (IOException ex) {
            throw new CustomException("Error reading the file.", ex.getMessage());
        } catch (RuntimeException ex) {
            throw new CustomException("Error uploading the file to Cloudinary.", ex.getMessage());
        }
    }
}
