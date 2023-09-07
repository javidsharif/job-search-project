package com.practice.jobsearchproject.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    <T> void uploadFile(MultipartFile file, T data) throws IOException;

    ByteArrayResource downloadFile(String fileName);

    List<String> listOfFiles();

    boolean deleteFile(String fileName);
}
