package edu.ntu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @RequestMapping("/input")
    public String input() {
        return "/staff/input";
    }
}
