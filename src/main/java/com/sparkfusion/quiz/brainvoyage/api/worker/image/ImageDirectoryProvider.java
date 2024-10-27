package com.sparkfusion.quiz.brainvoyage.api.worker.image;

import com.sparkfusion.quiz.brainvoyage.api.exception.UnexpectedException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageDirectoryProvider {

    private final String baseDirectory;

    public ImageDirectoryProvider(String baseDirectory) {
        this.baseDirectory = baseDirectory;
    }

    public String getDirectoryPath(ImageWorker.ImageType imageType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        String datePath = dateFormat.format(new Date());
        return baseDirectory + "/" + imageType.getDirectoryName() + "/" + datePath;
    }

    public void ensureDirectoryExists(ImageWorker.ImageType imageType) {
        String directoryPath = getDirectoryPath(imageType);
        File uploadDirFile = new File(directoryPath);
        if (!uploadDirFile.exists()) {
            if (!uploadDirFile.mkdirs()) {
                throw new UnexpectedException("Failed to create directory for storing images: " + directoryPath);
            }
        }
    }
}

