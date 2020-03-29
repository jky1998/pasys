package edu.ntu.controller;

import edu.ntu.bean.Department;
import edu.ntu.business.DepartmentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DepartmentBusiness departmentBusiness;

    @RequestMapping("/show")
    @ResponseBody
    public List<Department> showDepartments() {
        List<Department> departments = departmentBusiness.getDepartments();
        return departments;
    }
}
