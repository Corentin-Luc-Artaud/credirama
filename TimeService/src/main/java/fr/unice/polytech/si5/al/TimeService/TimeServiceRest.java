package fr.unice.polytech.si5.al.TimeService;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TimeServiceRest {
    private LocalDateTime curTime;

    public TimeServiceRest() {
        this.curTime = LocalDateTime.now();
    }
    
    @PostMapping("/")
    public void setTime(@RequestBody LocalDateTime time) {
        curTime = time;
    }

    @GetMapping("/")
    public LocalDateTime getTime() {
        return curTime;
    }
}