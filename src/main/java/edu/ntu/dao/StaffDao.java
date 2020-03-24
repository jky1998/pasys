package edu.ntu.dao;

import edu.ntu.bean.Staff;

public interface StaffDao {
    Staff findByName(String name);
}
