package edu.ntu.business;

import edu.ntu.bean.Message;
import edu.ntu.bean.Staff;
import edu.ntu.form.StaffInputForm;
import edu.ntu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffBusiness {
    @Autowired
    private StaffService staffService;

    // 员工信息录入
    public Message add(StaffInputForm form) {
        Message m = new Message();
        Staff staff = new Staff();
        Staff existSatff = staffService.findByNo(form.getNo());
        if (existSatff != null) {
            m.setFlag(false);
            m.setMsg("工号重复！");
            return m;
        } else {
            staff.setNo(form.getNo());
            staff.setName(form.getName());
            staff.setGender(form.getGender());
            staff.setPassword(form.getPassword());
            staff.setWorkdate(form.getWorkdate());
            staff.setPosition(form.getPosition());
            staff.setMail(form.getMail());
            staff.setPhone(form.getPhone());
            staff.setIdcard(form.getIdcard());
            staff.setAddress(form.getAddress());
            staff.getDepartment().setId(form.getDepartmentId());
            staffService.add(staff);
            m.setFlag(true);
            m.setMsg("添加成功！");
            return m;
        }
    }

}
