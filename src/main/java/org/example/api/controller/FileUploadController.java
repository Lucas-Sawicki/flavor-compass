package org.example.api.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.business.dao.FileUploadDAO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadDAO fileUploadDAO;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image")MultipartFile multipartFile,
                             Model model,
                             HttpSession session) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String imageURL = fileUploadDAO.uploadFile(multipartFile);
                session.setAttribute("imageUrl", imageURL);
                model.addAttribute("imageUrl" , imageURL);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Required part 'image' is not present.");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading file", e);
        }
        return "redirect:/owner/add/menu";
    }
}

