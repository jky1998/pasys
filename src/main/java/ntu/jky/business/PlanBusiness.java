package ntu.jky.business;

import ntu.jky.bean.Message;
import ntu.jky.bean.Plan;
import ntu.jky.enums.PlanType;
import ntu.jky.form.CommonGoalForm;
import ntu.jky.form.DeleteByIdForm;
import ntu.jky.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class PlanBusiness {
    @Autowired
    private PlanService planService;

    // 共性目标
    // 查共性目标
    public List<Plan> getCommonGoals() {
        List<Plan> goals = planService.findByType(PlanType.COMMON_GOAL);
        return goals;
    }

    // 添加共性目标
    public void addCommonGoal(CommonGoalForm form) {
        Plan plan = new Plan();
        plan.setType(PlanType.COMMON_GOAL);
        plan.setContent(form.getContent());
        plan.setDetail(form.getDetail());
        plan.setScore(form.getScore());
        planService.add(plan);
    }

    // 获得要修改的目标的信息
    public Plan getUpdatePlan(Integer id) {
        Plan plan = planService.findById(id);
        return plan;
    }

    // 修改共性目标
    public void updateCommonGoal(Integer id, CommonGoalForm form) {
        Plan plan = getUpdatePlan(id);
        plan.setScore(form.getScore());
        plan.setContent(form.getContent());
        plan.setDetail(form.getDetail());
        planService.update(plan);
    }

    // 计划制定
    // 查看月计划
    public List<Plan> getMonthlyPlans(Date monthly) {
        List<Plan> plans = planService.findByMonthly(monthly);
        return plans;
    }

    // 复制共性计划
    public Message copyCommonGoal(Date monthly) {
        List<Plan> goals = planService.findByType(PlanType.COMMON_GOAL);
        if (goals != null) {
            for (Plan goal : goals) {
                goal.setMonthly(monthly);
                goal.setType(PlanType.MONTHLY_PLAN);
                planService.add(goal);
            }
            return new Message(true, "复制成功！");
        } else {
            return new Message(false, "共性目标为空！");
        }
    }

    // 删除
    public void delete(DeleteByIdForm form) {
        int[] ids = form.getIds();
        planService.delete(ids);
    }
}
