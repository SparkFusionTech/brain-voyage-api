package com.sparkfusion.quiz.brainvoyage.api.worker.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public final class ImageWorker {

    private final String serverUrl;

    private final ImageDirectoryProvider directoryProvider;

    public ImageWorker(@Value("${SERVER_URL}") String serverUrl) {
        this.serverUrl = serverUrl;
        this.directoryProvider = new ImageDirectoryProvider(ImageUtils.UPLOAD_DIRECTORY);
    }

    public String getEmptyAccountIconUrl() {
        return serverUrl + ImageUtils.IMAGES_DIRECTORY + "/" + ImageUtils.EMPTY_ACCOUNT_ICON_NAME;
    }

    public String saveImage(MultipartFile image, ImageType imageType) throws IOException {
        directoryProvider.ensureDirectoryExists(imageType);
        String directoryPath = directoryProvider.getDirectoryPath(imageType);
        String fileName = UUID.randomUUID() + ".png";
        File file = new File(directoryPath, fileName);

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(image.getBytes());
        }

        return serverUrl + "/" + directoryPath + "/" + fileName;
    }

    public enum ImageType {

        ACCOUNT("accounts"),
        QUIZ("quizzes"),
        QUESTION("questions");

        private final String directoryName;

        ImageType(String directoryName) {
            this.directoryName = directoryName;
        }

        public String getDirectoryName() {
            return directoryName;
        }
    }
}