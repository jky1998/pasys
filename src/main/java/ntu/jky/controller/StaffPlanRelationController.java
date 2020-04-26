package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.bean.Progress;
import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.business.StaffPlanRelationBusiness;
import ntu.jky.form.DeleteByIdForm;
import ntu.jky.form.PlanInputForm;
import ntu.jky.form.SelfScoreAddForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "/addSelfScores", method = RequestMethod.POST)
    @ResponseBody
    public Message addSelfScores(@RequestBody SelfScoreAddForm form) {
        relationBusiness.addSelfScores(form);
        return new Message(true, "保存成功！");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Message delete(@RequestBody DeleteByIdForm form) {
        relationBusiness.delete(form);
        return new Message(true, "删除成功！");
    }

    // 根据用户查关联
    @RequestMapping(value = "/self/show/{monthly}", method = RequestMethod.POST)
    @ResponseBody
    public List<StaffPlanRelation> showSelfRelations(@PathVariable Date monthly) {
        return relationBusiness.showSelfRelations(monthly);
    }

    // 查看进度
    @RequestMapping(value = "/progress/show", method = RequestMethod.GET)
    @ResponseBody
    public List<Progress> showSelfProgress() {
        return relationBusiness.showSelfProgress();
    }
}
