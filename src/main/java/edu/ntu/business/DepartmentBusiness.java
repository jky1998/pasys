package edu.ntu.business;

import edu.ntu.bean.Department;
import edu.ntu.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DepartmentBusiness {
    @Autowired
    private DepartmentService departmentService;

    // 获取所有部门
    public List<Department> getDepartments() {
        List<Department> departments = departmentService.findAll();
        return departments;
    }
}
