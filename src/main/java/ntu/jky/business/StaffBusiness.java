package ntu.jky.business;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.Department;
import ntu.jky.bean.Message;
import ntu.jky.bean.Staff;
import ntu.jky.enums.RoleName;
import ntu.jky.form.DeleteByIdForm;
import ntu.jky.form.StaffInputForm;
import ntu.jky.form.StaffQueryForm;
import ntu.jky.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaffBusiness {
    @Autowired
    private StaffService staffService;

    /**
     * 员工管理页面查看员工信息
     * @param form
     * @return
     */
    public List<Staff> showStaff(StaffQueryForm form) {
        Staff staffQuery = new Staff();
        if (form.getNo() != null) {
            staffQuery.setNo(form.getNo());
        }
        if (form.getDepartmentId() != 0) {
            Department department = new Department();
            department.setId(form.getDepartmentId());
            staffQuery.setDepartment(department);
        }
        List<Staff> list = staffService.findAll(staffQuery);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        for (Staff staff : list) {
            String formatdate = df.format(staff.getWorkdate());
            staff.setFormatdate(formatdate);
        }
        return list;
    }

    /**
     * input staff
     * @param form
     * @return msg
     */
    public Message add(StaffInputForm form) {
        Message m = new Message();
        Staff staff = new Staff();

        Staff existStaff = staffService.findByNo(form.getNo());
        if (existStaff != null) {
            m.setFlag(false);
            m.setMsg("工号重复！");
            return m;
        } else {
            // add
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
            Department department = new Department();
            department.setId(form.getDepartmentId());
            staff.setDepartment(department);

            staffService.add(staff);
            m.setFlag(true);
            m.setMsg("添加成功！");
            return m;
        }
    }

    public Staff getUpdateStaff(Integer id) {
        Staff staff = staffService.findById(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formatdate = df.format(staff.getWorkdate());
        staff.setFormatdate(formatdate);
        return staff;
    }

    public Message update(Integer id, StaffInputForm form) {
        Message msg = new Message();
        Staff staff = staffService.findById(id);
        // no重复
        Staff existStaff = staffService.findByNo(form.getNo());
        if (existStaff != null && !(existStaff.getId().equals(id))) {
            msg.setFlag(false);
            msg.setMsg("工号" + existStaff.getNo() + "已存在！");
        } else {
            if (!(staff.getNo().equals(form.getNo()))) {
                staff.setNo(form.getNo());
            }
            if (!(staff.getName().equals(form.getName()))) {
                staff.setName(form.getName());
            }
            if (!(staff.getGender().equals(form.getGender()))) {
                staff.setGender(form.getGender());
            }
            if (!(staff.getPassword().equals(form.getPassword()))) {
                staff.setPassword(form.getPassword());
            }
            if (!(staff.getWorkdate().equals(form.getWorkdate()))) {
                staff.setWorkdate(form.getWorkdate());
            }
            if (!(staff.getPosition().equals(form.getPosition()))) {
                staff.setPosition(form.getPosition());
            }
            if (!(staff.getMail().equals(form.getMail()))) {
                staff.setMail(form.getMail());
            }
            if (!(staff.getPhone().equals(form.getPhone()))) {
                staff.setPhone(form.getPhone());
            }
            if (!(staff.getIdcard().equals(form.getIdcard()))) {
                staff.setIdcard(form.getIdcard());
            }
            if (!(staff.getAddress().equals(form.getAddress()))) {
                staff.setAddress(form.getAddress());
            }
            if (!(staff.getDepartment().getId().equals(form.getDepartmentId()))) {
                staff.getDepartment().setId(form.getDepartmentId());
            }
            staffService.update(staff);
            msg.setFlag(true);
            msg.setMsg("修改成功！");
        }
        return msg;
    }

    /**
     * delete staff(s)
     * @param form
     */
    public void delete(DeleteByIdForm form) {
        int[] ids = form.getIds();
        staffService.delete(ids);
    }

    // 根据部门查员工
    public List<Staff> findByDepartmentId(Integer id) {
        Staff staff = new Staff();
        if (id != 0) {
            Department department = new Department();
            department.setId(id);
            staff.setDepartment(department);
        }
        List<Staff> list = staffService.findAll(staff);
        return list;
    }

    // 部门评分显示员工
    public List<Staff> findEvaluationStaffs() {
        LoginStaff loginStaff = LoginStaff.getInstance();
        List<Staff> staffs = new ArrayList<>();
        if (loginStaff.getRoleId() == RoleName.ROLE_DEPARTMENT_LEADER) {
            staffs = findByDepartmentId(loginStaff.getDepartmentId());
        }
        return staffs;
    }
}
