package ntu.jky.form;

import java.util.Date;

public class ScoreForm {
    private Date monthly;
    private Float score;
    private Integer staffId;
    private Integer type;

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

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
