package ntu.jky.bean;

import java.util.Date;

/**
 * 计划类
 */
public class Plan {
    private Integer id;   // 编号
    private Date month;   // 月度
    private Integer type;   // 计划类型
    private Float score;    // 分值
    private String content;   // 目标内容
    private String detail;    // 详细信息（考核标准）
    private Plan affiliated;       // 计划从属关系

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

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

    public Plan getAffiliated() {
        return affiliated;
    }

    public void setAffiliated(Plan affiliated) {
        this.affiliated = affiliated;
    }
}
