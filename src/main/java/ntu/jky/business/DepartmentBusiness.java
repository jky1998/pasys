package ntu.jky.business;

import ntu.jky.bean.Department;
import ntu.jky.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DepartmentBusiness {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 获取所有部门
     * @return department
     */
    public List<Department> getDepartments() {
        List<Department> departments = departmentService.findAll();
        return departments;
    }
}
