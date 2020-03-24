package edu.ntu.controller;

import edu.ntu.bean.Department;
import edu.ntu.business.DepartmentBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    DepartmentBusiness departmentBusiness;

    @RequestMapping("/show")
    public void showDepartments(Model model) {
        List<Department> departments = departmentBusiness.getDepartments();
        model.addAttribute("departments", departments);
    }
}
