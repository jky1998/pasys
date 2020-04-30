package ntu.jky.controller;

import ntu.jky.bean.Message;
import ntu.jky.bean.Plan;
import ntu.jky.bean.Scores;
import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.business.PlanBusiness;
import ntu.jky.business.StaffPlanRelationBusiness;
import ntu.jky.form.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanBusiness planBusiness;
    @Autowired
    private StaffPlanRelationBusiness relationBusiness;

    // 共性目标
    @RequestMapping("/common")
    public String common(Model model) {
        List<Plan> goals = planBusiness.getCommonGoals();
        model.addAttribute("goals",goals);
        return "/plan/common";
    }

    // 添加共性目标
    @RequestMapping(value = "/common/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addCommonGoal(@RequestBody CommonGoalForm form) {
        planBusiness.addCommonGoal(form);
        return new Message(true, "添加成功！");
    }

    // 计划制定
    @RequestMapping("/formulate")
    public String formulate() {
        return "/plan/formulate";
    }

    // 查看计划
    @RequestMapping(value = "/formulate/monthly={monthly}", method = RequestMethod.POST)
    @ResponseBody
    public List<Plan> getMonthlyPlans(@PathVariable Date monthly) {
        List<Plan> plans = planBusiness.getMonthlyPlans(monthly);
        return plans;
    }

    // 复制上月计划
    @RequestMapping(value = "/copyLastPlan", method = RequestMethod.POST)
    @ResponseBody
    public Message copyLastPlan(@RequestBody CopyDateForm form) {
        return planBusiness.copyLastPlan(form);
    }

    // 添加计划明细
    @RequestMapping(value = "/monthly/add", method = RequestMethod.POST)
    @ResponseBody
    public Message addMonthlyPlan(@RequestBody MonthlyPlanForm form) {
        planBusiness.addMonthlyPlan(form);
        return new Message(true, "添加成功！");
    }

    // 复制共性目标
    @RequestMapping(value = "/copy/monthly={monthly}", method = RequestMethod.POST)
    @ResponseBody
    public Message copy(@PathVariable Date monthly) {
        Message message = planBusiness.copyCommonGoal(monthly);
        return message;
    }

    // 获取要修改的目标信息
    @RequestMapping(value = "/getUpdatePlan/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Plan getUpdatePlan(@PathVariable Integer id) {
        Plan plan = planBusiness.getUpdatePlan(id);
        return plan;
    }

    // 修改
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Message update(@PathVariable Integer id, @RequestBody CommonGoalForm form) {
        planBusiness.update(id, form);
        return new Message(true, "修改成功！");
    }

    // 删除
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Message delete(@RequestBody DeleteByIdForm form) {
        planBusiness.delete(form);
        return new Message(true,"删除成功！");
    }

    // 责任分解录入
    @RequestMapping("/input")
    public String input(){
        return "/plan/input";
    }

    // 责任分解管理
    @RequestMapping("/manage")
    public String manage(@ModelAttribute StaffPlanRelationQueryForm form, Model model) {
        List<StaffPlanRelation> relations = relationBusiness.getRelations(form);
        model.addAttribute("relations", relations);
        model.addAttribute("form", form);
        return "/plan/manage";
    }

    // 计划查询
    @RequestMapping("/index")
    public String index() {
        return "/plan/index";
    }

    // 查询
    @RequestMapping(value = "/index/query", method = RequestMethod.POST)
    @ResponseBody
    public List<StaffPlanRelation> showPlanQuery(@RequestBody StaffPlanRelationQueryForm form) {
        return planBusiness.showPlanQuery(form);
    }

    // 完成进度
    @RequestMapping(value = "/index/progress", method = RequestMethod.POST)
    @ResponseBody
    public List<Scores> showProgress(@RequestBody StaffPlanFindForm form) {
        return planBusiness.getScores(form);
    }

    // 责任分解查询
    @RequestMapping("/query")
    public String query(@ModelAttribute StaffPlanRelationQueryForm form, Model model) {
        List<StaffPlanRelation> relations = relationBusiness.getRelations(form);
        model.addAttribute("relations", relations);
        model.addAttribute("form", form);
        return "/plan/query";
    }
}
