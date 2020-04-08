package ntu.jky.dao;

import ntu.jky.bean.Department;

import java.util.List;

public interface DepartmentDao {
    List<Department> findAll();
}
