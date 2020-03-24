package edu.ntu.service;

import edu.ntu.bean.Staff;
import edu.ntu.dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    public Staff findByName(String name) {
        return staffDao.findByName(name);
    }
}
