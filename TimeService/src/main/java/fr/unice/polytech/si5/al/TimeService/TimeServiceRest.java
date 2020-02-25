package fr.unice.polytech.si5.al.TimeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    private long offset = 0;

    // private Random random;

    public TimeServiceRest() {
        this.curTimeMillis = System.currentTimeMillis();
        this.recoverTime = System.currentTimeMillis();
        failValue = Math.random() * 100;
    }

    @PostMapping(value = "/", consumes = "application/json")
    public String setTime(@RequestBody long timestamp) {
        System.out.println("-- Accessed POST        " + ldtString(timestamp));
        failValue = Math.random() * 100;
        curTimeMillis = timestamp;
        recoverTime = timestamp;
        return "OK";
    }

    @PostMapping(value = "/fail")
    public String failNow() {
        //curTimeMillis = curTimeMillis - (4 * 60 * 1000);
        offset = 4 * 60 * 1000;
        System.out.println("╔══════════════════════╗");
        System.out.println("║  Fail, time is now   ║ " + ldtString(System.currentTimeMillis() - offset));
        System.out.println("╚══════════════════════╝");
        failValue = -1;
        return "OK";
    }

    @PostMapping(value = "/slientrecover")
    public String SilentRecover() {
        offset = 0;
        //this.curTimeMillis = recoverTime;
        failValue = Math.random() * 100;
        return "OK";
    }

    @PostMapping(value = "/recover")
    public String Recover() {
        //RestTemplate resttemplate = new RestTemplate();
        //this.curTimeMillis = Long.parseLong(resttemplate.getForEntity(recoverUrl, String.class).getBody());
        offset = 0;
        //this.curTimeMillis = recoverTime;
        failValue = Math.random() * 100;
        System.out.println("╔══════════════════════╗");
        System.out.println("║ Recover, time is now ║ " + ldtString(System.currentTimeMillis()));
        System.out.println("╚══════════════════════╝");
        return "OK";
    }

    @GetMapping(value = "/getFail")
    public long getTimeWithFail() {
        tryFail();
        return getTime();
    }

    @GetMapping(value = "/")
    public long getTime() {
        System.out.println("-- Accessed GET          " + ldtString(System.currentTimeMillis() - offset));
        //return curTimeMillis;
        return System.currentTimeMillis() - offset;
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

    private String ldtString(long curTimeMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(curTimeMillis), ZoneId.systemDefault()).toLocalTime().toString();
    }
}
