package com.sparkfusion.quiz.brainvoyage.api.worker.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.UnexpectedException;
import java.util.UUID;

@Component
public final class ImageWorker {

    private final String serverUrl;

    public ImageWorker(@Value("${SERVER_URL}") String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public String saveImage(MultipartFile image) throws IOException {
        File uploadDirFile = new File(ImageUtils.IMAGES_DIRECTORY);
        if (!uploadDirFile.exists()) {
            if (!uploadDirFile.mkdirs()) throw new UnexpectedException("Failed to create directory for storing images");
        }

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        File file = new File(ImageUtils.IMAGES_DIRECTORY, fileName);
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(image.getBytes());
        }

        return serverUrl + ImageUtils.IMAGES_DIRECTORY + "/" + fileName;
    }
}
