package ntu.jky.business;

import ntu.jky.bean.Plan;
import ntu.jky.bean.Staff;
import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.form.ResponseBreakdownForm;
import ntu.jky.service.StaffPlanRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffPlanRelationBusiness {
    @Autowired
    private StaffPlanRelationService relationService;

    // 添加关联
    public void add(ResponseBreakdownForm form) {
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
