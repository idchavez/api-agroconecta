package com.apiagroconecta.api_agroconecta.service.storage;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadImageService {

    private final String bucketName;
    private final Storage storage;

    // Inyectamos las propiedades directamente en el constructor
    public UploadImageService(
            @Value("${gcp.bucket.name}") String bucketName,
            @Value("${spring.cloud.gcp.credentials.location}") String gcpConfigFile) throws IOException {

        this.bucketName = bucketName;

        // Carga el archivo gcp-credentials.json desde resources
        ClassPathResource resource = new ClassPathResource(gcpConfigFile);
        GoogleCredentials credentials = GoogleCredentials.fromStream(resource.getInputStream());

        this.storage = StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(file.getContentType())
                .build();
        storage.create(blobInfo, file.getBytes());
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }

    public String uploadBase64(String base64Image) throws IOException {
        String contentType = "image/png";
        String extension = "png";
        byte[] decodedBytes;

        if (base64Image.startsWith("data:")) {
            String[] parts = base64Image.split(",");
            String metadata = parts[0];
            String base64Data = parts[1];
            contentType = metadata.substring(metadata.indexOf(":") + 1, metadata.indexOf(";"));
            extension = contentType.substring(contentType.indexOf("/") + 1);
            decodedBytes = java.util.Base64.getDecoder().decode(base64Data);
        } else {
            decodedBytes = java.util.Base64.getDecoder().decode(base64Image);
        }

        String fileName = UUID.randomUUID().toString() + "." + extension;
        BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, fileName)
                .setContentType(contentType)
                .build();
        storage.create(blobInfo, decodedBytes);

        return String.format("https://storage.googleapis.com/%s/%s", bucketName, fileName);
    }
}