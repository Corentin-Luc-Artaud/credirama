package fr.unice.polytech.si5.al.TimeService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/")
public class TimeServiceRest {

    @Value("${failpercentage:5}") // default 1/20
    private double percentage_of_fail;

    private LocalDateTime curTime;
    private double failValue;
    //private Random random;

    public TimeServiceRest() {
        this.curTime = LocalDateTime.now();
        //this.random = new Random(System.nanoTime());
        failValue = Math.random()*100;
    }

    @PostMapping(value = "/", consumes = "text/plain")
    public String setTime(@RequestBody String string) {
        //System.out.println("Accessed POST - " + string);
        failValue = Math.random()*100;
        curTime = LocalDateTime.parse(string);
        return "OK";
    }

    @PostMapping("/fail")
    public void failNow() {
        curTime = curTime.minusMinutes(4);
        failValue = -1;
    }

    @GetMapping(value = "/")
    public String getTimeWithFail() {
        tryFail();
        return getTime();
    }

    public String getTime() {
        //System.out.println("Accessed GET - " + curTime);
        return curTime.toString();
    }


    /**
     * actualy fail one on 10 times
     */
    public void tryFail() {
        if (failValue <= percentage_of_fail && failValue >= 0) {
            Logger.getLogger(this.getClass().getName()).info("Fail !!!");
            curTime = curTime.minusMinutes(4);
            failValue = -1;
        }else if(failValue > 0){
            failValue -= percentage_of_fail;
        }
    }
}