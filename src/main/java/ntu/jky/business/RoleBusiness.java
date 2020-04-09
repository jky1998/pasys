package ntu.jky.business;

import ntu.jky.bean.Role;
import ntu.jky.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleBusiness {
    @Autowired
    private RoleService roleService;

    public List<Role> showRoles() {
        return roleService.findAll();
    }
}
