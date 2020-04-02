package edu.ntu.service;

import edu.ntu.bean.Staff;
import edu.ntu.dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    public List<Staff> findAll() {
        return staffDao.findAll();
    }

    public Staff findByName(String name) {
        return staffDao.findByName(name);
    }

    public Staff findByNo(String no) {
        return staffDao.findByNo(no);
    }

    public void add(Staff staff) {
        staffDao.add(staff);
    }
}
