package ntu.jky.service;

import ntu.jky.bean.StaffPlanRelation;
import ntu.jky.dao.StaffPlanRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffPlanRelationService {
    @Autowired
    private StaffPlanRelationDao relationDao;

    public List<StaffPlanRelation> findAll(StaffPlanRelation relation) {
        return relationDao.findAll(relation);
    }

    public void add(StaffPlanRelation relation) {
        relationDao.add(relation);
    }

    public void update(StaffPlanRelation relation) {
        relationDao.update(relation);
    }

    public void delete(int[] ids) {
        relationDao.delete(ids);
    }
}
