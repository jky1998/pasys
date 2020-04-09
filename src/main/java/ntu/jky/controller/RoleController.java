package ntu.jky.controller;

import ntu.jky.bean.Role;
import ntu.jky.business.RoleBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleBusiness roleBusiness;

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public List<Role> showRoles() {
        List<Role> roles = roleBusiness.showRoles();
        return roles;
    }
}
