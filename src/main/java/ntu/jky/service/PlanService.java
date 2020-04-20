package ntu.jky.service;

import ntu.jky.bean.Plan;
import ntu.jky.dao.PlanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlanService {
    @Autowired
    private PlanDao planDao;

    // 根据目标类型查询
    public List<Plan> findByType(int type) {
        Plan plan = new Plan();
        plan.setType(type);
        return planDao.findAll(plan);
    }

    // 根据目标id查询
    public Plan findById(Integer id) {
        Plan plan = new Plan();
        plan.setId(id);
        List<Plan> list = planDao.findAll(plan);
        if (list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    // 根据月度查询
    public List<Plan> findByMonthly(Date monthly) {
        Plan plan = new Plan();
        plan.setMonthly(monthly);
        List<Plan> plans = planDao.findAll(plan);
        return plans;
    }

    // 添加
    public void add(Plan plan) {
        planDao.add(plan);
    }

    // 更新
    public void update(Plan plan) {
        planDao.update(plan);
    }

    // 删除
    public void delete(int[] ids) {
        planDao.delete(ids);
    }
}
