package ntu.jky.controller;

import ntu.jky.bean.Authority;
import ntu.jky.bean.Staff;
import ntu.jky.business.AuthorityBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityBusiness authorityBusiness;

    @RequestMapping("/index")
    public String authority(HttpSession session, Model model) {
        Staff staff = (Staff)session.getAttribute("staff");
        if (staff != null) {
            model.addAttribute("staff", staff);
            return "/authority/index";
        } else {
            model.addAttribute("msg", "登陆过期，请重新登陆！");
            return "/common/login";
        }
    }

    @RequestMapping(value = "/show", method = RequestMethod.POST)
    @ResponseBody
    public List<Authority> show(@PathVariable Integer roleId) {
        List<Authority> authorities = authorityBusiness.getAuthorities(roleId);
        return authorities;
    }
}
