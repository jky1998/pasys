package ntu.jky.service;

import ntu.jky.bean.Department;
import ntu.jky.dao.DepartmentDao;
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
