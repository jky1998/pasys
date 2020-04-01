package edu.ntu.dao;

import edu.ntu.bean.Staff;

public interface StaffDao {
    Staff findByName(String name);
    Staff findByNo(String no);
    void add(Staff staff);           // 人员信息录入
}
