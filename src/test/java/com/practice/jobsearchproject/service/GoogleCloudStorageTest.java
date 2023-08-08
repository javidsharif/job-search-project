package com.practice.jobsearchproject.service;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.practice.jobsearchproject.service.impl.GoogleCloudStorageService;
import com.practice.jobsearchproject.service.impl.ImageService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.ByteArrayInputStream;
import static org.mockito.Mockito.*;

public class GoogleCloudStorageTest {
    @Autowired
    private ImageService imageService; // Зависимость, которую вы хотите протестировать

    @Mock
    private GoogleCloudStorageService googleCloudStorageService; // Заглушка для сервиса Google Cloud Storage

    @Mock
    private Storage storageMock;

    @Mock
    private Bucket bucketMock;


    @Test
    public void testUploadImage() {
        // Создаем входной поток для изображения (здесь используется пример с ByteArrayInputStream)
        ByteArrayInputStream imageInputStream = new ByteArrayInputStream(new byte[1024]);

        // Создаем заглушку для googleCloudStorageService.getStorage()
        when(googleCloudStorageService.getStorage()).thenReturn(storageMock);
        when(storageMock.get("your-bucket-name")).thenReturn(bucketMock);

        // Вызываем метод загрузки изображения
        imageService.uploadImage(imageInputStream, "your-bucket-name", "image.jpg");

        // Проверяем, что методы были вызваны с правильными аргументами
        verify(googleCloudStorageService, times(1)).getStorage();
        verify(storageMock, times(1)).get("your-bucket-name");
        verify(bucketMock, times(1)).create(eq("image.jpg"), eq(imageInputStream));
    }


}
