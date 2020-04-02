package edu.ntu.controller;

import edu.ntu.bean.Message;
import edu.ntu.bean.Staff;
import edu.ntu.business.StaffBusiness;
import edu.ntu.form.StaffInputForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffBusiness staffBusiness;

    @RequestMapping("/input")
    public String input(HttpSession session, Model model) {
        Staff staff = (Staff)session.getAttribute("staff");
        model.addAttribute("staff", staff);
        return "/staff/input";
    }

    @RequestMapping("/manage")
    public String manage(HttpSession session, Model model) {
        Staff staff = (Staff)session.getAttribute("staff");
        List<Staff> staffList = staffBusiness.findAll();
        model.addAttribute("staff", staff);
        model.addAttribute("staffList", staffList);
        return "/staff/manage";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addStaff(@RequestBody StaffInputForm form) {
        Message message = staffBusiness.add(form);
        return message;
    }
}
