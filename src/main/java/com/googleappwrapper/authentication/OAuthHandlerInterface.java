package com.googleappwrapper.authentication;

import com.google.api.client.auth.oauth2.Credential;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface OAuthHandlerInterface {

    Credential authenticate() throws IOException, GeneralSecurityException;
}
