package ntu.jky.business;

import ntu.jky.bean.Staff;
import ntu.jky.form.LoginForm;
import ntu.jky.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonBusiness {
    @Autowired
    private StaffService staffService;

    /**
     * 验证密码
     * @param form
     * @return true || false
     */
    public boolean checkPassword(LoginForm form) {
        boolean flag;
        Staff loginStaff = staffService.findByNo(form.getNo());
        if (loginStaff == null) {
            flag = false;
        } else if (form.getPassword().equals(loginStaff.getPassword())) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * 获取登陆人员
     * @param form
     * @return staff
     */
    public Staff getLoginStaff(LoginForm form) {
        Staff staff = staffService.findByNo(form.getNo());
        return staff;
    }
}
