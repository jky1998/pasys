package ntu.jky.bean;

import java.util.Date;

public class Score {
    private Integer id;      // 编号
    private Date monthly;    // 月度
    private Float score;     // 评分
    private Integer type;    // 类型
    private Staff assessor;  // 考核人
    private Staff staff;     // 被考核员工

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMonthly() {
        return monthly;
    }

    public void setMonthly(Date monthly) {
        this.monthly = monthly;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Staff getAssessor() {
        return assessor;
    }

    public void setAssessor(Staff assessor) {
        this.assessor = assessor;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
