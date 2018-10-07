package com.googleappwrapper;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.googleappwrapper.authentication.OAuthHandlerInterface;
import com.googleappwrapper.authentication.OAuthServiceHandler;

import java.io.IOException;
import java.util.Properties;

public class AppConfig extends AbstractModule {

    private static final String APP_PROPERTIES_FILE = "app.properties";
    private Properties properties;

    public AppConfig() throws IOException {
        properties = new Properties();
        properties.load(AppConfig.class.getClassLoader().getResourceAsStream(APP_PROPERTIES_FILE));
    }

    @Override
    protected void configure() {
        bind(OAuthHandlerInterface.class).to(OAuthServiceHandler.class);
        bindConstant().annotatedWith(Names.named("filePath")).to(properties.getProperty("FILE_PATH"));
    }
}
