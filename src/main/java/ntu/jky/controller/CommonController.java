package ntu.jky.controller;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.Staff;
import ntu.jky.business.CommonBusiness;
import ntu.jky.form.LoginForm;
import ntu.jky.form.PasswordChangeForm;
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
            // 保存登陆用户的信息
            commonBusiness.saveLoginStaff(form);
            LoginStaff loginStaff = LoginStaff.getInstance();
            model.addAttribute("loginStaff", loginStaff);
            return "/plan/index";
        } else {
            model.addAttribute("msg", "用户名或密码错误！");
            return "/common/login";
        }
    }

    // 修改密码
    @RequestMapping("/password")
    public String changePassword() {
        return "/common/password";
    }

    // 修改密码
    @RequestMapping("/check/password")
    public String checkChangePassword(@ModelAttribute PasswordChangeForm form, Model model) {
        boolean flag = commonBusiness.changePassword(form);
        if (flag) {
            model.addAttribute("msg", "修改成功！请重新登陆");
            return "/common/login";
        } else {
            model.addAttribute("msg", "密码不一致！");
            return "/common/password";
        }
    }
}
