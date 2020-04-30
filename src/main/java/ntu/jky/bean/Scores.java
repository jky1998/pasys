package ntu.jky.bean;

/**
 * 计划查询 完成进度
 */
public class Scores {
    private String type;  // 评分类型
    private Float total;  // 总分
    private Float score;  // 得分

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
