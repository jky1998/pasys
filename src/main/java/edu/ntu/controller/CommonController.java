package edu.ntu.controller;

import edu.ntu.business.CommonBusiness;
import edu.ntu.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/common")
public class CommonController {
    @Autowired
    CommonBusiness commonBusiness;

    @RequestMapping("/head")
    public String showHead() {
        return "/common/head";
    }

    @RequestMapping("/nav")
    public String showNav() {
        return "/common/nav";
    }

    @RequestMapping("/login")
    public String login() {
        return "/common/login";
    }

    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkPassword(@ModelAttribute LoginForm form, Model model) {
        boolean flag = commonBusiness.checkPassword(form);
        if (flag) {
            return "/staff/input";
        } else {
            model.addAttribute("msg", "用户名或密码错误！");
            return "/common/login";
        }
    }
}
