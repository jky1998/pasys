package edu.ntu.dao;

import edu.ntu.bean.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
}
