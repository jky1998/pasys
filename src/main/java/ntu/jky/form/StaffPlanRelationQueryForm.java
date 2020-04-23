package ntu.jky.form;

import java.util.Date;

public class StaffPlanRelationQueryForm {
    private Integer departmentId;
    private Integer staffId;
    private Integer yyyy;
    private Integer mm;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getYyyy() {
        return yyyy;
    }

    public void setYyyy(Integer yyyy) {
        this.yyyy = yyyy;
    }

    public Integer getMm() {
        return mm;
    }

    public void setMm(Integer mm) {
        this.mm = mm;
    }
}
