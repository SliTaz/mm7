package com.zbensoft.mmsmp.sms.ra.http.client;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

public class SpringRestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080/mmsmp/BindBody";

    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders() {
        String plainCredentials = "bill:abc123";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static void createUser() {
        System.out.println("\nTesting create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Object> request = new HttpEntity<Object>(getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/", request);
        System.out.println("Location : " + uri.toASCIIString());
    }

}
