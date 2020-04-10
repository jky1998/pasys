package ntu.jky.controller;

import ntu.jky.bean.Authority;
import ntu.jky.bean.Message;
import ntu.jky.bean.Staff;
import ntu.jky.business.AuthorityBusiness;
import ntu.jky.business.RoleAuthorityRelationBusiness;
import ntu.jky.form.AuthorityUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityBusiness authorityBusiness;
    @Autowired
    private RoleAuthorityRelationBusiness relationBusiness;

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

    @RequestMapping(value = "/show/{roleId}", method = RequestMethod.POST)
    @ResponseBody
    public List<Authority> show(@PathVariable Integer roleId) {
        List<Authority> authorities = authorityBusiness.getAuthorities(roleId);
        return authorities;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Message update(@RequestBody AuthorityUpdateForm form) {
        relationBusiness.updateRelations(form);
        return new Message(true, "保存成功！");
    }
}
