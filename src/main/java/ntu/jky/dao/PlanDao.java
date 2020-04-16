package ntu.jky.dao;

import ntu.jky.bean.Plan;

import java.util.List;

public interface PlanDao {
    List<Plan> findAll(Plan plan);
    void add(Plan plan);
    void update(Plan plan);
    void delete(int[] ids);
}
