package fr.unice.polytech.si5.al.TimeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/")
public class TimeServiceRest {

    @Value("${failpercentage:5}") // default 1/20
    private double percentage_of_fail;

    private long curTimeMillis;
    private double failValue;
    //private Random random;

    public TimeServiceRest() {
        this.curTimeMillis = System.currentTimeMillis();
        failValue = Math.random()*100;
    }

    @PostMapping(value = "/", consumes = "text/plain")
    public String setTime(@RequestBody long timestamp) {
        //System.out.println("Accessed POST - " + string);
        failValue = Math.random()*100;
        curTimeMillis = timestamp;
        return "OK";
    }

    @PostMapping("/fail")
    public void failNow() {
        curTimeMillis = curTimeMillis-(4*60*1000);
        failValue = -1;
    }

    @PostMapping("/recover")
    public void Recover() {

    }

    @GetMapping(value = "/")
    public long getTimeWithFail() {
        tryFail();
        return getTime();
    }

    public long getTime() {
        //System.out.println("Accessed GET - " + curTime);
        return curTimeMillis;
    }


    /**
     * actualy fail one on 10 times
     */
    public void tryFail() {
        if (percentage_of_fail <= 0) return;
        if (failValue <= percentage_of_fail && failValue >= 0) {
            Logger.getLogger(this.getClass().getName()).info("Fail !!!");
            failNow();
        }else if(failValue > 0){
            failValue -= percentage_of_fail;
        }
    }
}