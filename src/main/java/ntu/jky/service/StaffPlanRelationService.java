package ntu.jky.service;

import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.dao.StaffPlanRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffPlanRelationService {
    @Autowired
    private StaffPlanRelationDao relationDao;

    public void add(StaffPlanRelation relation) {
        relationDao.add(relation);
    }
}
