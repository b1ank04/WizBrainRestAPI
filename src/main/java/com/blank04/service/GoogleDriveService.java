package com.blank04.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleDriveService {

    @Value("${api.google.application_name}")
    private String appName;

    @Value("${api.google.credentials}")
    private String credentialsPath;

    private String parent = "1rMmB70bfTJNE78RLyUbxvxemgkXLJ9vn";

    public String uploadFileToGoogleDrive(String fileName, String mimeType, byte[] fileBytes) throws IOException, GeneralSecurityException {
        File fileMetadata = new File();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(List.of(parent));
        ByteArrayContent mediaContent = new ByteArrayContent(mimeType, fileBytes);
        File uploadedFile = getDriveService().files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        return "https://drive.google.com/uc?export=download&id=" + uploadedFile.getId();
    }

    public Drive getDriveService() throws IOException, GeneralSecurityException {
        Credential credential = GoogleCredential.fromStream(new FileInputStream("\\" + credentialsPath))
                .createScoped(Collections.singleton(DriveScopes.DRIVE));
        return new Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential)
                .setApplicationName(appName)
                .build();
    }
}
