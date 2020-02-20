package fr.unice.polytech.si5.al.TimeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/")
public class TimeServiceRest {

    @Value("${failpercentage:5}") // default 1/20
    private double percentage_of_fail;

    // @Value("${recoverUrl:http://localhost:9081/}")
    // private String recoverUrl;

    private long recoverTime;

    private long curTimeMillis;
    private double failValue;
    // private Random random;

    public TimeServiceRest() {
        this.curTimeMillis = System.currentTimeMillis();
        failValue = Math.random() * 100;
    }

    @PostMapping(value = "/", consumes = "application/json")
    public String setTime(@RequestBody long timestamp) {
        System.out.println("---- Accessed POST - " + timestamp + " ----");
        failValue = Math.random() * 100;
        curTimeMillis = timestamp;
        recoverTime = timestamp;
        return "OK";
    }

    @PostMapping(value = "/fail")
    public String failNow() {

        curTimeMillis = curTimeMillis - (4 * 60 * 1000);
        System.out.println("---- Fail time is now " + curTimeMillis + " ----");
        failValue = -1;
        return "OK";
    }

    @PostMapping(value = "/recover")
    public String Recover() {
        //RestTemplate resttemplate = new RestTemplate();
        //this.curTimeMillis = Long.parseLong(resttemplate.getForEntity(recoverUrl, String.class).getBody());
        this.curTimeMillis = recoverTime;
        failValue = Math.random() * 100;
        System.out.println("---- Recover time is now " + curTimeMillis + " ----");
        return "OK";
    }

    @GetMapping(value = "/getFail")
    public long getTimeWithFail() {
        tryFail();
        return getTime();
    }

    @GetMapping(value = "/")
    public long getTime() {
        System.out.println("----Accessed GET - " + curTimeMillis + " ----");
        return curTimeMillis;
    }

    /**
     * actualy fail one on 10 times
     */
    public void tryFail() {
        if (percentage_of_fail <= 0)
            return;
        if (failValue <= percentage_of_fail && failValue >= 0) {
            Logger.getLogger(this.getClass().getName()).info("Fail !!!");
            failNow();
        } else if (failValue > 0) {
            failValue -= percentage_of_fail;
        }
    }
}
