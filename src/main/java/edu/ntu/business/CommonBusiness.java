package edu.ntu.business;

import edu.ntu.bean.Staff;
import edu.ntu.form.LoginForm;
import edu.ntu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonBusiness {
    @Autowired
    private StaffService staffService;

    /**
     * 验证登陆密码
     * @param form
     */
    public boolean checkPassword(LoginForm form) {
        boolean flag;
        Staff loginStaff = staffService.findByName(form.getName());
        if (form.getPassword().equals(loginStaff.getPassword())) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }
}
