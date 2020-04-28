package ntu.jky.business;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.*;
import ntu.jky.form.*;
import ntu.jky.service.StaffPlanRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class StaffPlanRelationBusiness {
    @Autowired
    private StaffPlanRelationService relationService;

    // 责任分解管理查关联
    public List<StaffPlanRelation> getRelations(StaffPlanRelationQueryForm form) {
        StaffPlanRelation relation = new StaffPlanRelation();
        Staff staff = new Staff();
        if (form.getDepartmentId() != null && form.getDepartmentId() != 0) {
            Department department = new Department();
            department.setId(form.getDepartmentId());
            staff.setDepartment(department);
            relation.setStaff(staff);
        }
        if (form.getStaffId() != null && form.getStaffId() != 0) {
            staff.setId(form.getStaffId());
            relation.setStaff(staff);
        }
        if (form.getYyyy() != null && form.getMm() != null && form.getYyyy() != 0 && form.getMm() != 0) {
            Date monthly;
            GregorianCalendar gc = new GregorianCalendar();
            gc.set(Calendar.YEAR,form.getYyyy());//设置年
            gc.set(Calendar.MONTH, form.getMm() - 1);//这里0是1月..以此向后推
            gc.set(Calendar.DAY_OF_MONTH, 1);//设置天
            monthly = gc.getTime();
            Plan plan = new Plan();
            plan.setMonthly(monthly);
            relation.setPlan(plan);
        }
        List<StaffPlanRelation> relations = relationService.findAll(relation);
        return relations;
    }

    // 添加关联
    public void add(PlanInputForm form) {
        Integer[] staffIds = form.getStaffIds();
        Integer[] planIds = form.getPlanIds();

        for (int i = 0; i < staffIds.length; i++) {
            StaffPlanRelation relation = new StaffPlanRelation();
            Staff staff = new Staff();
            staff.setId(staffIds[i]);
            relation.setStaff(staff);
            for (int j = 0; j < planIds.length; j++) {
                Plan plan = new Plan();
                plan.setId(planIds[j]);
                relation.setPlan(plan);
                relationService.add(relation);
            }
        }
    }

    // 根据用户查关联
    public List<StaffPlanRelation> showSelfRelations(Date monthly) {
        LoginStaff loginStaff = LoginStaff.getInstance();
        StaffPlanRelation relation = new StaffPlanRelation();
        if (loginStaff != null) {
            Staff staff = new Staff();
            staff.setId(loginStaff.getId());
            relation.setStaff(staff);
        }
        if (monthly != null) {
            Plan plan = new Plan();
            plan.setMonthly(monthly);
            relation.setPlan(plan);
        }
        return relationService.findAll(relation);
    }

    // 自我评分
    public void addSelfScores(SelfScoreAddForm form) {
        for (StaffPlanRelation relation : form.getRelations()) {
            relationService.update(relation);
        }
    }

    // 查看最近三个月的评分情况
    public List<Progress> showSelfProgress() {
        Date d1 = new Date();
        StaffPlanRelation findRelation = new StaffPlanRelation();
        LoginStaff loginStaff = LoginStaff.getInstance();
        Staff staff = new Staff();
        staff.setId(loginStaff.getId());
        findRelation.setStaff(staff);

        List<Progress> progresses = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Progress progress = new Progress();
            progress.setMonthly(d1);
            float percent;
            Plan plan = new Plan();
            plan.setMonthly(d1);
            findRelation.setPlan(plan);
            List<StaffPlanRelation> relations = relationService.findAll(findRelation);
            if (relations.size() != 0) {
                float k = 0;
                for (StaffPlanRelation relation : relations) {
                    if (relation.getScore() != null && relation.getScore() != -1) {
                        k++;
                    }
                }
                percent = k / relations.size();
            } else {
                percent = 0f;
            }
            progress.setPercent(percent);
            progresses.add(progress);
            d1 = lastMonth(d1);
        }
        return progresses;
    }

    // 获取当前月份的前一个月
    public Date lastMonth(Date dNow) {
        Date dBefore;
        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.MONTH, -1);  //设置为前3月
        dBefore = calendar.getTime();   //得到前3月的时间
        return dBefore;
    }

    // 删除
    public void delete(DeleteByIdForm form) {
        int[] ids = form.getIds();
        relationService.delete(ids);
    }

    // 部门评分
    // 查看员工计划
    public List<StaffPlanRelation> showEvaluatedPlans(StaffPlanFindForm form) {
        StaffPlanRelation relation = new StaffPlanRelation();
        if (form.getStaffId() != null) {
            Staff staff = new Staff();
            staff.setId(form.getStaffId());
            relation.setStaff(staff);
        }
        if (form.getMonthly() != null) {
            Plan plan = new Plan();
            plan.setMonthly(form.getMonthly());
            relation.setPlan(plan);
        }
        return relationService.findAll(relation);
    }
}
