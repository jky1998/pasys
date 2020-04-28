package ntu.jky.business;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.Authority;
import ntu.jky.bean.Staff;
import ntu.jky.form.LoginForm;
import ntu.jky.service.RoleAuthorityRelationService;
import ntu.jky.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommonBusiness {
    @Autowired
    private StaffService staffService;
    @Autowired
    private AuthorityBusiness authorityBusiness;

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

    /**
     * 保存登陆用户的信息
     * @param form
     */
    public void saveLoginStaff(LoginForm form) {
        Staff staff = staffService.findByNo(form.getNo());
        Integer departmentId = staff.getDepartment().getId();
        Integer roleId = staff.getRole().getId();
        List<Authority> authorities = authorityBusiness.getAuthorities(roleId);

        LoginStaff loginStaff = LoginStaff.getInstance();
        loginStaff.setId(staff.getId());
        loginStaff.setRoleId(roleId);
        loginStaff.setName(staff.getName());
        loginStaff.setDepartmentId(departmentId);
        loginStaff.setAuthorities(authorities);
    }
}
