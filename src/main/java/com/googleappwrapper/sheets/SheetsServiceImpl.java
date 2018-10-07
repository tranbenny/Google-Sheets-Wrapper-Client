package com.googleappwrapper.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.inject.Inject;
import com.googleappwrapper.authentication.OAuthServiceHandler;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static com.googleappwrapper.AppConfig.APPLICATION_NAME;

public class SheetsServiceImpl implements SheetsService {

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private Credential token;
    private Sheets sheetsClient;

    @Inject
    public SheetsServiceImpl(OAuthServiceHandler authenticationHandler) throws IOException, GeneralSecurityException {
        this.token = authenticationHandler.authenticate();
        this.sheetsClient = buildSheetClient();
    }

    private Sheets buildSheetClient() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.token)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Spreadsheet getSpreadsheet(String spreadsheetID) {
        try {
            return sheetsClient.spreadsheets()
                    .get(spreadsheetID)
                    .execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
