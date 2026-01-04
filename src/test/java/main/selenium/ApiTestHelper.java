package main.selenium;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ApiTestHelper {

    private static final String BASE_URL = "http://localhost:8080";

    private static HttpHeaders formHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    public static void signup(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        String body = "userName=" + username + "&password=" + password;
        HttpEntity<String> request = new HttpEntity<>(body, formHeaders());

        restTemplate.postForEntity(
                BASE_URL + "/user/signUp",
                request,
                String.class
        );
    }

    public static String loginAndGetToken(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();

        String body = "userName=" + username + "&password=" + password;
        HttpEntity<String> request = new HttpEntity<>(body, formHeaders());

        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE_URL + "/user/login",
                request,
                String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Login başarısız: " + response.getBody());
        }

        String responseBody = response.getBody();
        if (responseBody == null || !responseBody.contains("token")) {
            throw new RuntimeException("Token bulunamadı! Yanıt: " + responseBody);
        }

        return responseBody.substring(
                responseBody.indexOf("token is:") + 9
        ).trim();
    }

    public static String signupAndLogin(String username, String password) {
        signup(username, password);
        return loginAndGetToken(username, password);
    }
}
