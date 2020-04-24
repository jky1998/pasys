package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.business.StaffPlanRelationBusiness;
import ntu.jky.form.DeleteByIdForm;
import ntu.jky.form.PlanInputForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/response")
public class StaffPlanRelationController {
    @Autowired
    private StaffPlanRelationBusiness relationBusiness;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Message add(@RequestBody PlanInputForm form) {
        relationBusiness.add(form);
        return new Message(true, "添加成功！");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Message delete(@RequestBody DeleteByIdForm form) {
        relationBusiness.delete(form);
        return new Message(true, "删除成功！");
    }
}
