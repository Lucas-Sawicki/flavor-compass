package org.example.api.controller.mockitoTests;

import org.example.api.controller.FileUploadController;
import org.example.business.dao.FileUploadDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileUploadDAOControllerMockitoTest {

    @InjectMocks
    FileUploadController fileUploadController;

    @Mock
    FileUploadDAO fileUploadDAO;

    @Mock
    Model model;

    @Test
    public void whenUploadFileThenBackToAddingPage() throws IOException {
        //given
        MultipartFile multipartFile = mock(MultipartFile.class);
        //when
        when(multipartFile.isEmpty()).thenReturn(false);
        when(fileUploadDAO.uploadFile(multipartFile)).thenReturn("imageUrl");
        String viewName = fileUploadController.uploadFile(multipartFile, model, new MockHttpSession());
        //then
        assertEquals("redirect:/owner/add/menu", viewName);
    }
    @Test
    public void whenUploadFileThenShouldThrowsException() {
        //given
        MultipartFile multipartFile = mock(MultipartFile.class);
        //when
        when(multipartFile.isEmpty()).thenReturn(true);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            fileUploadController.uploadFile(multipartFile, model, new MockHttpSession());
        });
    }

    @Test
    public void whenUploadFileThenShouldThrowsIOException() throws IOException {
        //given
        MultipartFile multipartFile = mock(MultipartFile.class);
        //when
        when(multipartFile.isEmpty()).thenReturn(false);
        doThrow(IOException.class).when(fileUploadDAO).uploadFile(multipartFile);
        //then
        assertThrows(ResponseStatusException.class, () -> {
            fileUploadController.uploadFile(multipartFile, model, new MockHttpSession());
        });
    }
}
