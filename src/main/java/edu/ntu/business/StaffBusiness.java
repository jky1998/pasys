package edu.ntu.business;

import edu.ntu.bean.Staff;
import edu.ntu.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StaffBusiness {
    @Autowired
    private StaffService staffService;

    public void add(Staff staff) {

    }

}
