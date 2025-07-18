package ntu.jky.bean;

import java.util.Date;

/**
 * 完成进度 月度 + 百分比
 */
public class Progress {
    private Date monthly;
    private Department department;
    private Float percent;

    public Date getMonthly() {
        return monthly;
    }

    public void setMonthly(Date monthly) {
        this.monthly = monthly;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }
}
