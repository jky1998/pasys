package edu.ntu.service;

import edu.ntu.bean.Department;
import edu.ntu.dao.DepartmentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    public List<Department> findAll() {
        return departmentDao.findAll();
    }
}
