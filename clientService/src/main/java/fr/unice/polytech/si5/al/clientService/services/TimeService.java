package fr.unice.polytech.si5.al.clientService.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Service
@EnableConfigurationProperties
@ConfigurationProperties(value = "time-service")
public class TimeService {
    final Logger mLogger = LoggerFactory.getLogger(TimeService.class);
    private String url;

    @PostConstruct
    public void onInit() {
        System.out.println("url: " + url);
    }

    public long getCurrentTime() {
        if (this.url == null) {
            return new Date().getTime();
        }

        try {
            URL url = new URL(this.url);
            RestTemplate restTemplate = new RestTemplate();
            return Long.parseLong(restTemplate.getForEntity(url.toString(), String.class).getBody());
        } catch (Exception e) {
            mLogger.error(e.getMessage());
            return new Date().getTime();
        }
    }

    public void recoverAtomicTime() {
        try {
            URL url = new URL(this.url + "recover");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(url.toString(), null, Void.class);
        } catch (MalformedURLException e) {
            mLogger.error(e.getMessage());
        }
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
