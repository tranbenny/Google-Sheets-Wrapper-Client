package com.googleappwrapper.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/**
 * Perform OAuth2 authentication for Google Sheets API
 *  - Requires client_id, client_secret generated from Google Sheets API
 */
@Singleton
public class OAuthServiceHandler implements OAuthHandlerInterface {

    private static final Long TOKEN_EXPIRATION_SECONDS = 3600L;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);

    private String filePath;
    private String tokensPath;
    private Credential credentials;

    @Inject
    public OAuthServiceHandler(@Named("filePath") String filePath,
                               @Named("tokensPath") String tokensPath) {
        this.filePath = filePath;
        this.tokensPath = tokensPath;
    }

    @Override
    public Credential authenticate() throws IOException, GeneralSecurityException {
        if (credentials == null || credentials.getExpiresInSeconds() <= 0) {
            credentials = getTokenCredentials();
        }
        return this.credentials;
    }

    private Credential getTokenCredentials() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        FileInputStream fileReader = new FileInputStream(filePath);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(fileReader));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
                JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokensPath)))
                .setAccessType("offline")
                .build();
        Credential credentials = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        credentials.setExpiresInSeconds(TOKEN_EXPIRATION_SECONDS);
        return credentials;
    }
}
