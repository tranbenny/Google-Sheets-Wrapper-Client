package com.googleappwrapper.sheets;

import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.googleappwrapper.AppConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

// INTEGRATION TEST
public class SheetsServiceImplTest {

    private SheetsServiceImpl sheetsService;

    @BeforeClass
    public void setup() throws IOException {
        Injector injector = Guice.createInjector(new AppConfig());
        sheetsService = (SheetsServiceImpl) injector.getInstance(SheetsService.class);
    }

    @Test
    public void testGetSpreadsheetWithValidId() {
        String expectedSpreadsheetId = "1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms";
        Spreadsheet spreadsheet = sheetsService.getSpreadsheet(expectedSpreadsheetId);
        assertNotNull(spreadsheet);
        assertEquals(spreadsheet.getSpreadsheetId(), expectedSpreadsheetId);
    }
}
