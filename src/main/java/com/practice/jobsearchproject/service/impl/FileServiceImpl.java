package com.practice.jobsearchproject.service.impl;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.practice.jobsearchproject.exception.FileUploadException;
import com.practice.jobsearchproject.model.entity.Company;
import com.practice.jobsearchproject.model.entity.User;
import com.practice.jobsearchproject.service.FileService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class FileServiceImpl implements FileService {
    @Setter
    @Value("${gcp.bucket.name}")
    private String bucketName;

    @Autowired
    private Storage storage;

    private static final String[] ALLOWED_EXTENSIONS = new String[] {"jpg", "jpeg", "png"};


    @Override
    public List<String> listOfFiles() {
        List<String> list = new ArrayList<>();
        Page<Blob> blobs = storage.list(bucketName);
        for (Blob blob: blobs.iterateAll()) {
           list.add(blob.getName());
        }
        return list;
    }

    @Override
    public boolean deleteFile(String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        return blob.delete();
    }

    @Override
    public ByteArrayResource downloadFile(String fileName) {
        Blob blob = storage.get(bucketName, fileName);
        ByteArrayResource resource = new ByteArrayResource(
                blob.getContent());
        return resource;
    }
    @Override
    public <T> void uploadFile(MultipartFile file, T data) throws IOException {
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(fileExtension)) {
            throw new FileUploadException("File type \"" + fileExtension + "\" is not supported.");
        }
        BlobId blobId = BlobId.of(bucketName, Objects.requireNonNull(generateUniqueFileName(fileName)));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).
                setContentType(file.getContentType()).build();
        Blob blob = storage.create(blobInfo,file.getBytes());
        String photoUrl = createImageUrl(blob.getBlobId().getName());
        if(data instanceof User) {
            ((User) data).setPhotoUrl(photoUrl);
        }else if (data instanceof Company) {
            ((Company) data).setPhotoUrl(photoUrl);
        }
    }
    public String createImageUrl(String objectName) {
        return "https://storage.googleapis.com/" + bucketName + "/" + objectName;
    }

    public static String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return UUID.randomUUID().toString() + extension;
    }
}
