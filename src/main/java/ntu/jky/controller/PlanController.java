package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.bean.Plan;
import ntu.jky.business.PlanBusiness;
import ntu.jky.form.CommonGoalForm;
import ntu.jky.form.DeleteByIdForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanBusiness planBusiness;

    // 共性目标
    @RequestMapping("/common")
    public String common(Model model) {
        List<Plan> goals = planBusiness.getCommonGoals();
        model.addAttribute("goals",goals);
        return "/plan/common";
    }

    // 计划制定
    @RequestMapping("/formulate")
    public String formulate(Model model) {
        return "/plan/formulate";
    }

    // 添加共性目标
    @RequestMapping(value = "/common/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addCommonGoal(@RequestBody CommonGoalForm form) {
        planBusiness.addCommonGoal(form);
        return new Message(true, "添加成功！");
    }

    // 获取要修改的共性目标信息
    @RequestMapping(value = "/getUpdatePlan/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Plan getUpdatePlan(@PathVariable Integer id) {
        Plan plan = planBusiness.getUpdatePlan(id);
        return plan;
    }

    // 修改共性目标
    @RequestMapping(value = "/common/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Message updateCommonGoal(@PathVariable Integer id, @RequestBody CommonGoalForm form) {
        planBusiness.updateCommonGoal(id, form);
        return new Message(true, "修改成功！");
    }

    // 删除
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Message detete(@RequestBody DeleteByIdForm form) {
        planBusiness.delete(form);
        return new Message(true,"删除成功！");
    }
}
