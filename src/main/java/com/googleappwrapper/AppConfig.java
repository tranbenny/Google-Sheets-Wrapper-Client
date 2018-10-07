package com.googleappwrapper;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.googleappwrapper.authentication.OAuthHandlerInterface;
import com.googleappwrapper.authentication.OAuthServiceHandler;
import com.googleappwrapper.sheets.SheetsService;
import com.googleappwrapper.sheets.SheetsServiceImpl;

import java.io.IOException;
import java.util.Properties;

public class AppConfig extends AbstractModule {

    private static final String APP_PROPERTIES_FILE = "app.properties";

    public static final String APPLICATION_NAME = "GOOGLE SHEETS REQUESTER";

    private Properties properties;

    public AppConfig() throws IOException {
        properties = new Properties();
        properties.load(AppConfig.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE));
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named("filePath")).to(properties.getProperty("FILE_PATH"));
        bindConstant().annotatedWith(Names.named("tokensPath")).to(properties.getProperty("TOKEN_PATH"));

        bind(OAuthHandlerInterface.class).to(OAuthServiceHandler.class).in(Singleton.class);
        bind(SheetsService.class).to(SheetsServiceImpl.class);
    }
}
