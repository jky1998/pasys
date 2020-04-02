package edu.ntu.dao;

import edu.ntu.bean.Staff;

import java.util.List;

public interface StaffDao {
    List<Staff> findAll();
    Staff findByName(String name);
    Staff findByNo(String no);
    void add(Staff staff);           // 人员信息录入
}
