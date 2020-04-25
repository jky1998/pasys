package ntu.jky.dao;

import ntu.jky.bean.StaffPlanRelation;

import java.util.List;

public interface StaffPlanRelationDao {
    List<StaffPlanRelation> findAll(StaffPlanRelation relation);
    void add(StaffPlanRelation relation);    // 添加
    void update(StaffPlanRelation relation);
    void delete(int[] ids);
}
