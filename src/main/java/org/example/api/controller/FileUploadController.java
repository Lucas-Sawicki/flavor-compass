package org.example.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.api.dto.MenuItemDTO;
import org.example.business.FileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    // U u

    private final FileUpload fileUpload;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("image")MultipartFile multipartFile,
                             Model model,
                             HttpSession session) {
        try {
            if (multipartFile != null && !multipartFile.isEmpty()) {
                String imageURL = fileUpload.uploadFile(multipartFile);
                session.setAttribute("imageUrl", imageURL);
                model.addAttribute("imageUrl" , imageURL);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file provided");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading file", e);
        }
        return "redirect:/owner/add/menu";
    }
}

