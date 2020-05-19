package ntu.jky.business;

import ntu.jky.bean.*;
import ntu.jky.enums.PlanType;
import ntu.jky.enums.ScoreType;
import ntu.jky.form.*;
import ntu.jky.service.PlanService;
import ntu.jky.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlanBusiness {
    @Autowired
    private PlanService planService;
    @Autowired
    private StaffPlanRelationBusiness relationBusiness;
    @Autowired
    private ScoreService scoreService;


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

    // 计划制定
    // 查看月计划
    public List<Plan> getMonthlyPlans(Date monthly) {
        List<Plan> plans = planService.findByMonthly(monthly);
        return plans;
    }

    // 复制上月计划
    public Message copyLastPlan(CopyDateForm form) {
        List<Plan> plans = planService.findByMonthly(form.getLastMonthly());
        if (plans.size() != 0) {
            for (Plan plan : plans) {
                plan.setMonthly(form.getMonthly());
                planService.add(plan);
            }
            return new Message(true, "复制成功！");
        } else {
            return new Message(false, "上月计划为空！");
        }
    }

    // 添加计划明细
    public void addMonthlyPlan(MonthlyPlanForm form) {
        Plan plan = new Plan();
        plan.setType(PlanType.MONTHLY_PLAN);
        plan.setContent(form.getContent());
        plan.setDetail(form.getDetail());
        plan.setMonthly(form.getMonthly());
        plan.setScore(form.getScore());
        planService.add(plan);
    }

    // 复制共性计划
    public Message copyCommonGoal(Date monthly) {
        List<Plan> goals = planService.findByType(PlanType.COMMON_GOAL);
        if (goals.size() != 0) {
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

    // 修改
    public void update(Integer id, CommonGoalForm form) {
        Plan plan = getUpdatePlan(id);
        plan.setScore(form.getScore());
        plan.setContent(form.getContent());
        plan.setDetail(form.getDetail());
        planService.update(plan);
    }

    // 删除
    public void delete(DeleteByIdForm form) {
        int[] ids = form.getIds();
        planService.delete(ids);
    }

    // 计划查询
    public List<StaffPlanRelation> showPlanQuery(StaffPlanRelationQueryForm form) {
        // 找员工计划关联
        List<StaffPlanRelation> relations = relationBusiness.getRelations(form);
        List<StaffPlanRelation> newRelations = new ArrayList<>();

        Plan plan = new Plan();
        Date monthly;
        GregorianCalendar gc = new GregorianCalendar();
        gc.set(Calendar.YEAR, 0);//设置年
        gc.set(Calendar.MONTH, 0);
        gc.set(Calendar.DAY_OF_MONTH, 1);//设置天
        monthly = gc.getTime();
        plan.setMonthly(monthly);
        Staff staff = new Staff();
        for (StaffPlanRelation relation : relations) {
            if (staff.getId() == null || !(staff.getId().equals(relation.getStaff().getId()))) {
                staff.setId(relation.getStaff().getId());
                plan.setMonthly(relation.getPlan().getMonthly());
                newRelations.add(relation);
            } else if(relation.getPlan().getMonthly() != null) {
                if (!sameDate(plan.getMonthly(), relation.getPlan().getMonthly())) {
                    plan.setMonthly(relation.getPlan().getMonthly());
                    newRelations.add(relation);
                }
            }
        }
        return newRelations;
    }

    // 完成进度
    public List<Scores> getScores(StaffPlanFindForm form) {
        List<Scores> scores = new ArrayList<>();
        List<StaffPlanRelation> relations = relationBusiness.showEvaluatedPlans(form);

        float total = 0;
        float selfScore = 0;
        // 自我评分
        for (StaffPlanRelation relation : relations) {
            total += relation.getPlan().getScore();
            if (relation.getScore() != null) {
                if (relation.getScore() != -1) {
                    selfScore += relation.getScore();
                }
            }
        }
        Scores scores1 = new Scores();
        scores1.setType("自我评分");
        scores1.setTotal(total);
        scores1.setScore(selfScore);
        scores.add(scores1);

        // 部门评分
        Scores scores2 = new Scores();
        scores2.setType("部门评分");
        scores2.setTotal(total);
        Score score = new Score();
        Staff staff = new Staff();
        staff.setId(form.getStaffId());
        score.setStaff(staff);
        score.setType(ScoreType.DEPARTMENT);
        score.setMonthly(form.getMonthly());
        Score sc = scoreService.findByStaff(score);
        if (sc != null) {
            scores2.setScore(sc.getScore());
        } else {
            scores2.setScore(0f);
        }
        scores.add(scores2);

        // 考核组评分
        score.setType(ScoreType.ASSESSOR);
        List<Score> assessorScores = scoreService.findAll(score);
        for (Score assessorScore : assessorScores) {
            Scores scores3 = new Scores();
            scores3.setType("考核组评分(" + assessorScore.getAssessor().getName() + ")");
            scores3.setTotal(total);
            if (assessorScore != null) {
                scores3.setScore(assessorScore.getScore());
            } else {
                scores3.setScore(0f);
            }
            scores.add(scores3);
        }

        return scores;
    }

    public boolean sameDate(Date d1, Date d2) {
        if(null == d1 || null == d2)
            return false;
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(d1);
        cal1.set(Calendar.DAY_OF_MONTH, 1);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        cal2.set(Calendar.DAY_OF_MONTH, 1);
        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);
        return  cal1.getTime().equals(cal2.getTime());
    }
}
