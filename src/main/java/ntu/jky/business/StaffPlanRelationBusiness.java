package ntu.jky.business;

import ntu.jky.bean.Department;
import ntu.jky.bean.Plan;
import ntu.jky.bean.Staff;
import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.form.PlanInputForm;
import ntu.jky.form.StaffPlanRelationQueryForm;
import ntu.jky.service.StaffPlanRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@Component
public class StaffPlanRelationBusiness {
    @Autowired
    private StaffPlanRelationService relationService;

    // 查关联
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
}
