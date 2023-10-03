package com.alex.service;

import com.alex.model.ResourceDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class RestService {

    @Value("${song.service.url}")
    private String songServiceBaseUrl;

    private final RestTemplate restTemplate;

    public void send(ResourceDetails resourceDetails) {
        String url = songServiceBaseUrl + "songs";
        log.info("Connection URL: {}", url);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, resourceDetails, String.class);

            HttpStatusCode statusCode = response.getStatusCode();
            if (!statusCode.is2xxSuccessful()) {
                throw new RuntimeException(response.getBody());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }
}