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
    public void setTime(@RequestBody String string) {
        System.out.println("Accessed POST");
        curTime = LocalDateTime.parse(string);
    }

    @GetMapping(value = "/")
    public LocalDateTime getTime() {
        System.out.println("Accessed GET");
        return curTime;
    }
}