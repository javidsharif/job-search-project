package com.practice.jobsearchproject.service.impl;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoogleCloudStorageService {
    @Getter
    private final Storage storage;

    public GoogleCloudStorageService() throws IOException {
        // Загрузите ключ служебного аккаунта из вашего JSON-файла
        String serviceAccountKeyPath = "/Users/99451/Downloads/job-search-project-395115-3f2f481bfdeb.json";

        // Создайте экземпляр клиента Google Cloud Storage
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(serviceAccountKeyPath)))
                .build();

        this.storage = storageOptions.getService();
    }


}
