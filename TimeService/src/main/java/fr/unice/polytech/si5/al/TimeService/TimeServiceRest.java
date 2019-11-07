package fr.unice.polytech.si5.al.TimeService;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/")
public class TimeServiceRest {
    private LocalDateTime curTime;

    public TimeServiceRest() {
        this.curTime = LocalDateTime.now();
    }

    @PostMapping(value = "/", consumes = "text/plain")
    public String setTime(@RequestBody String string) {
        System.out.println("Accessed POST - " + string);
        curTime = LocalDateTime.parse(string);
        return "OK";
    }

    @GetMapping(value = "/")
    public String getTime() {
        System.out.println("Accessed GET - " + curTime);
        return curTime.toString();
    }
}