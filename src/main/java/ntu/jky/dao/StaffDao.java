package ntu.jky.dao;

import ntu.jky.bean.Staff;

import java.util.List;

public interface StaffDao {
    List<Staff> findAll(Staff staff);
    void add(Staff staff);
    void update(Staff staff);
    void delete(int[] ids);
}
