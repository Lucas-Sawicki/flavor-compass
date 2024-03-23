package org.example.business;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class FileUploadImplMockitoTest {

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private FileUploadImpl fileUploadImpl;

    @Test
    public void shouldReturnUrlCorrectly() throws Exception {
        // given
        MultipartFile multipartFile = mock(MultipartFile.class);
        Uploader uploader = mock(Uploader.class);
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://example.com/image.jpg");

        when(multipartFile.getBytes()).thenReturn(new byte[0]);
        when(cloudinary.uploader()).thenReturn(uploader);
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(uploadResult);

        // when
        String result = fileUploadImpl.uploadFile(multipartFile);

        // then
        assertThat(result, is("http://example.com/image.jpg"));
    }

}