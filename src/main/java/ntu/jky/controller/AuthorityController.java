package ntu.jky.controller;

import ntu.jky.bean.Authority;
import ntu.jky.bean.Message;
import ntu.jky.business.AuthorityBusiness;
import ntu.jky.business.RoleAuthorityRelationBusiness;
import ntu.jky.form.AuthorityUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/authority")
public class AuthorityController {
    @Autowired
    private AuthorityBusiness authorityBusiness;
    @Autowired
    private RoleAuthorityRelationBusiness relationBusiness;

    @RequestMapping("/index")
    public String authority() {
        return "/authority/index";
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
