package com.googleappwrapper.sheets;

import com.google.api.services.sheets.v4.model.Spreadsheet;

public interface SheetsService {
    Spreadsheet getSpreadsheet(String spreadsheetID);
}
