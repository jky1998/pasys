package edu.ntu.controller;

import edu.ntu.bean.Message;
import edu.ntu.bean.Staff;
import edu.ntu.business.StaffBusiness;
import edu.ntu.form.StaffInputForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private StaffBusiness staffBusiness;

    @RequestMapping("/input")
    public String input() {
        return "/staff/input";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addStaff(@ModelAttribute StaffInputForm form) {
        Message message = staffBusiness.add(form);
        return message;
    }
}
