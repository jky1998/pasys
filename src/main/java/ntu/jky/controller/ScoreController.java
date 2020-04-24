package ntu.jky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/score")
public class ScoreController {
    @RequestMapping("/self")
    public String self() {
        return "/score/self";
    }
}
