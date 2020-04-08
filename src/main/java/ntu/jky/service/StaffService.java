package ntu.jky.service;

import ntu.jky.bean.Staff;
import ntu.jky.dao.StaffDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;

    public List<Staff> findAll(Staff staff) {
        return staffDao.findAll(staff);
    }

    public Staff findById(Integer id) {
        Staff staff = new Staff();
        staff.setId(id);
        List<Staff> staffList = staffDao.findAll(staff);
        if (staffList.size() != 0) {
            return staffList.get(0);
        } else {
            return null;
        }
    }

    public Staff findByNo(String no) {
        Staff staff = new Staff();
        staff.setNo(no);
        List<Staff> staffList = staffDao.findAll(staff);
        if (staffList.size() != 0) {
            return staffList.get(0);
        } else {
            return null;
        }
    }

    public Staff findByName(String name) {
        Staff staff = new Staff();
        staff.setName(name);
        List<Staff> staffList = staffDao.findAll(staff);
        if (staffList.size() != 0) {
            return staffList.get(0);
        } else {
            return null;
        }
    }

    public void add(Staff staff) {
        staffDao.add(staff);
    }

    public void update(Staff staff) {
        staffDao.update(staff);
    }

    public void delete(int[] ids) {
        staffDao.delete(ids);
    }
}
