package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.bean.Staff;
import ntu.jky.business.StaffBusiness;
import ntu.jky.form.StaffDeleteForm;
import ntu.jky.form.StaffInputForm;
import ntu.jky.form.StaffQueryForm;
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

    // staff/input.html
    @RequestMapping("/input")
    public String input(HttpSession session, Model model) {
        Staff staff = (Staff)session.getAttribute("staff");
        if (staff != null) {
            model.addAttribute("staff", staff);
            return "/staff/input";
        } else {
            model.addAttribute("msg", "登陆过期，请重新登陆！");
            return "/common/login";
        }
    }

    @RequestMapping("/manage")
    public String manage(@ModelAttribute StaffQueryForm form, HttpSession session, Model model) {
        Staff staff = (Staff)session.getAttribute("staff");
        List<Staff> staffList = staffBusiness.showStaff(form);
        if (staff != null) {
            model.addAttribute("staff", staff);
            model.addAttribute("staffList", staffList);
            return "/staff/manage";
        } else {
            model.addAttribute("msg", "登陆过期，请重新登陆！");
            return "/common/login";
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@RequestBody StaffInputForm form) {
        Message message = staffBusiness.add(form);
        return message;
    }

    // 获取要修改的员工信息
    @RequestMapping(value = "/getUpdateStaff/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Staff getUpdateStaff(@PathVariable Integer id) {
        Staff staff = staffBusiness.getUpdateStaff(id);
        return staff;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Message update(@PathVariable Integer id, @RequestBody StaffInputForm form) {
        Message message = staffBusiness.update(id, form);
        return message;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@RequestBody StaffDeleteForm form) {
        staffBusiness.delete(form);
        return "success";
    }
}
