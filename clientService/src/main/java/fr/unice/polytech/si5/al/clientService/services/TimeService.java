package fr.unice.polytech.si5.al.clientService.services;

import fr.unice.polytech.si5.al.clientService.kafka.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.time.LocalDateTime;

@Service
@ConfigurationProperties(value = "time-service")
public class TimeService {
    final Logger mLogger = LoggerFactory.getLogger(Producer.class);
    private String url;

    public LocalDateTime getCurrentTime() {
        if (this.url == null ) {
            return LocalDateTime.now();
        }
        try {
            java.net.URL url = new URL(this.url);
            RestTemplate restTemplate = new RestTemplate();
            return LocalDateTime.parse(restTemplate.getForEntity(url.toString(), String.class).getBody());
        } catch (Exception e) {
            mLogger.error(e.getMessage());
            return LocalDateTime.now();
        }
    }
}
