package com.sparkfusion.quiz.brainvoyage.api.worker.image;

import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Component
public class ImageWorker {

    private final S3Client s3Client;
    private final String bucketName;
    private final String serverUrl;

    public ImageWorker(
            @Value("${s3.bucket-name}") String bucketName,
            @Value("${s3.region}") String region,
            @Value("${s3.endpoint-url}") String endpointUrl
    ) {
        this.bucketName = bucketName;
        this.serverUrl = endpointUrl + "/" + bucketName;

        String accessKey = System.getenv("S3_ACCESS_KEY");
        String secretKey = System.getenv("S3_SECRET_KEY");

        this.s3Client = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .region(Region.of(region))
                .endpointOverride(URI.create(endpointUrl))
                .build();
    }

    public String saveImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        try {
            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromBytes(image.getBytes())
            );
        } catch (S3Exception e) {
            throw new UnexpectedException("Failed to save image");
        }

        return serverUrl + "/" + fileName;
    }

    public String getEmptyAccountIconUrl() {
        return serverUrl + "/" + ImageUtils.EMPTY_ACCOUNT_ICON_NAME;
    }
}