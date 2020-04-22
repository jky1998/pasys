package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.business.StaffPlanRelationBusiness;
import ntu.jky.form.ResponseBreakdownForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/response")
public class StaffPlanRelationController {
    @Autowired
    private StaffPlanRelationBusiness relationBusiness;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@RequestBody ResponseBreakdownForm form) {
        relationBusiness.add(form);
        return new Message(true, "添加成功！");
    }
}
