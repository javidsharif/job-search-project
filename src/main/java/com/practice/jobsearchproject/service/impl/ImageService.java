package com.practice.jobsearchproject.service.impl;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class ImageService {
    @Autowired
    private GoogleCloudStorageService googleCloudStorageService;

    public void uploadImage(InputStream imageInputStream, String bucketName, String imageName) {
        Bucket bucket = googleCloudStorageService.getStorage().get(bucketName);
        BlobId blobId = BlobId.of(bucketName, imageName);
        Blob blob = bucket.create(String.valueOf(blobId), imageInputStream);
    }
}
