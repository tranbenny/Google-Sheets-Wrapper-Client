package com.googleappwrapper.authentication;

import com.google.api.client.auth.oauth2.Credential;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.googleappwrapper.AppConfig;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

// INTEGRATION TEST
public class OAuthServiceHandlerTest {

    @Inject
    private OAuthServiceHandler oAuthServiceHandler;

    @BeforeClass
    public void setup() throws IOException {
        Injector injector = Guice.createInjector(new AppConfig());
        oAuthServiceHandler = (OAuthServiceHandler) injector.getInstance(OAuthHandlerInterface.class);
    }

    // TODO: ADD CLEANUP STEP FOR TOKENS
    @Test
    public void testAuthenticateCallsUseTheSameCredentials() throws IOException, GeneralSecurityException {
        Credential tokenCredentials = oAuthServiceHandler.authenticate();
        assertNotNull(tokenCredentials);
        assertEquals(tokenCredentials, oAuthServiceHandler.authenticate());
    }

}
