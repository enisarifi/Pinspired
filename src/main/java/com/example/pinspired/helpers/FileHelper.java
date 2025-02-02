package com.example.pinspired.helpers;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.Base64;

import org.springframework.web.multipart.MultipartFile;

public class FileHelper {
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    static {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    public static String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        file.transferTo(filePath.toFile());

        return fileName;
    }

    // 🌐 2️⃣ Save File from URL or Base64 String
    public static String saveFileFromUrl(String fileUrl) throws IOException {
        if (fileUrl == null || fileUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("File URL cannot be empty");
        }

        if (fileUrl.startsWith("data:image/")) {
            // Handle Base64-encoded image data
            return saveFileFromBase64(fileUrl);
        } else {
            // Handle normal URL
            return saveFileFromNormalUrl(fileUrl);
        }
    }

    // 🖼️ 3️⃣ Save File from Base64 String
    private static String saveFileFromBase64(String base64Image) throws IOException {
        String fileName = System.currentTimeMillis() + "_image.jpg";
        Path filePath = Paths.get(UPLOAD_DIR, fileName);

        String base64Data = base64Image.split(",")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Data);
        Files.write(filePath, decodedBytes);

        return fileName;
    }

    private static String saveFileFromNormalUrl(String fileUrl) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        try (InputStream in = new URL(fileUrl).openStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }

    public static void deleteFile(String fileName) throws IOException {
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.deleteIfExists(filePath);
    }
}
