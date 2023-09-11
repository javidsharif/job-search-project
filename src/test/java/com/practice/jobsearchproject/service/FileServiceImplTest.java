package com.practice.jobsearchproject.service;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.practice.jobsearchproject.config.security.SecurityConfig;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.service.impl.FileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@Import(SecurityConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FileServiceImplTest {

    @Mock
    private Storage storage;

    @Mock
    private Blob blob;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    @InjectMocks
    private FileServiceImpl fileService;

//    @Test
//    public void testListOfFiles() {
//        List<String> expectedFiles = Arrays.asList("file1.jpg", "file2.png");
//
////        BlobId blobId = BlobId.of(bucketName, "file1.jpg");
////        BlobId blobId2 = BlobId.of(bucketName, "file2.jpg");
//
//        Mockito.when(storage.list(bucketName)).thenReturn((Page<Blob>) new PageImpl<>(Collections.singletonList(blob)));
//
//        List<String> actualFiles = fileService.listOfFiles();
//
//        Assertions.assertEquals(expectedFiles, actualFiles);
//    }
//
//    @Test
//    public void testUploadFile() throws IOException {
//        MultipartFile multipartFile = new MockMultipartFile("file", "test.txt", "text/plain", "file content".getBytes());
//        User user = new User();
//
//        BlobId blobId = BlobId.of(bucketName, "uniqueFileName.txt");
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
//                .setContentType(multipartFile.getContentType())
//                .build();
//        Blob createdBlob = Mockito.mock(Blob.class);
//        Mockito.when(storage.create(blobInfo, multipartFile.getBytes())).thenReturn(createdBlob);
//
//        fileService.uploadFile(multipartFile, user);
//
//        Assertions.assertNotNull(user.getPhotoUrl());
//    }
//
//    @Test
//    public void testCreateImageUrl() {
//        String objectName = "file1.jpg";
//        String expectedUrl = "https://storage.googleapis.com/" + bucketName + "/" + objectName;
//
//        fileService = new FileServiceImpl(storage);
//        String actualUrl = fileService.createImageUrl(objectName);
//
//        Assertions.assertEquals(expectedUrl, actualUrl);
//    }

    @Test
    public void testDeleteFile() {
        String fileName = "file1.jpg";

        Mockito.when(storage.get(bucketName, fileName)).thenReturn(blob);

        Mockito.when(blob.delete()).thenReturn(true);

        boolean deleted = fileService.deleteFile(fileName);

        Assertions.assertTrue(deleted);
        Mockito.verify(blob).delete();
    }

    @Test
    public void testDownloadFile() {
        String fileName = "file1.jpg";
        byte[] content = "file content".getBytes();
        Mockito.when(storage.get(bucketName, fileName)).thenReturn(blob);
        Mockito.when(blob.getContent()).thenReturn(content);

        ByteArrayResource resource = fileService.downloadFile(fileName);

        Assertions.assertNotNull(resource);
        Assertions.assertArrayEquals(content, resource.getByteArray());
    }

    @Test
    public void testGenerateUniqueFileName() {
        String originalFileName = "file.txt";

        String uniqueFileName = FileServiceImpl.generateUniqueFileName(originalFileName);

        Assertions.assertNotNull(uniqueFileName);
        Assertions.assertTrue(uniqueFileName.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}\\.txt"));
    }
}

