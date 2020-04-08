package ntu.jky.controller;

import ntu.jky.bean.Staff;
import ntu.jky.business.CommonBusiness;
import ntu.jky.form.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/common")
@SessionAttributes("staff")
public class CommonController {
    @Autowired
    CommonBusiness commonBusiness;

    // login.html
    @RequestMapping("/login")
    public String login() {
        return "/common/login";
    }

    // 登陆验证
    @RequestMapping(value = "/check", method = RequestMethod.POST)
    public String checkPassword(@ModelAttribute LoginForm form, Model model) {
        boolean flag = commonBusiness.checkPassword(form);
        if (flag) {
            Staff staff = commonBusiness.getLoginStaff(form);
            // 将staff存储在session中
            model.addAttribute("staff", staff);
            return "/staff/input";
        } else {
            model.addAttribute("msg", "用户名或密码错误！");
            return "/common/login";
        }
    }
}
