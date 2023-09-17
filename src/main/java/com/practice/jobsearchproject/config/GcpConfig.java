package com.practice.jobsearchproject.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.heroku.api.App;
import com.heroku.api.HerokuAPI;
import com.practice.jobsearchproject.exception.RequestFailedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;


@Configuration
public class GcpConfig {
    @Bean
    public GoogleCredentials customGoogleCredentials() throws IOException {
        String googleAppCred = "GOOGLE_APPLICATION_CREDENTIALS";
        String apiKey = "c10c6dc8-b304-465b-9242-454181b277d1";
        String appName = "job-search-project";
        try {
            HerokuAPI api = new HerokuAPI(apiKey);

            App app = api.getApp(appName);
            Map<String, String> configVars = api.listConfig(app.getId());
            String encodedCredentials = configVars.get(googleAppCred);
            byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
            String decodedJSON = new String(decodedBytes);
            GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(decodedJSON.getBytes()));
            return credentials;
        } catch (RequestFailedException exception) {
            throw new RequestFailedException("Request failed Exception");
        }
    }
}
