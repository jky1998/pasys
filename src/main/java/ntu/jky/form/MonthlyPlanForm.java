package ntu.jky.form;

import java.util.Date;

public class MonthlyPlanForm {
    private String content;   // 目标内容
    private String detail;    // 详细信息
    private Float score;    // 分值
    private Date monthly;     // 月度

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Date getMonthly() {
        return monthly;
    }

    public void setMonthly(Date monthly) {
        this.monthly = monthly;
    }
}
