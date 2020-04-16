package ntu.jky.form;

/**
 * 共性目标 之 添加 更新
 */
public class CommonGoalForm {
    private String content;       // 目标内容
    private String detail;        // 考核内容
    private Float score;          // 分值

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
}
