package fr.unice.polytech.si5.al.clientService.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.time.LocalDateTime;

@Service
@EnableConfigurationProperties
@ConfigurationProperties(value = "time-service")
public class TimeService {
    final Logger mLogger = LoggerFactory.getLogger(TimeService.class);
    private String url;

    @PostConstruct
    public void onInit() {
      System.out.println("url: "+url);
    }

    public LocalDateTime getCurrentTime() {
        if (this.url == null ) {
            return LocalDateTime.now();
        }
        try {
            java.net.URL url = new URL(this.url);
            RestTemplate restTemplate = new RestTemplate();
            return LocalDateTime.parse(restTemplate.getForEntity(url.toString(), String.class).getBody());
        } catch (Exception e) {
            System.err.println(e);
            return LocalDateTime.now();
        }
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
