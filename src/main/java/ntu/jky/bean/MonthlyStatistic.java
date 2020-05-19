package ntu.jky.bean;

public class MonthlyStatistic {
    private StaffPlanRelation relation;
    private Float selfScore;
    private Float departScore;
    private Float assessorScore;
    private Float average;

    public StaffPlanRelation getRelation() {
        return relation;
    }

    public void setRelation(StaffPlanRelation relation) {
        this.relation = relation;
    }

    public Float getSelfScore() {
        return selfScore;
    }

    public void setSelfScore(Float selfScore) {
        this.selfScore = selfScore;
    }

    public Float getDepartScore() {
        return departScore;
    }

    public void setDepartScore(Float departScore) {
        this.departScore = departScore;
    }

    public Float getAssessorScore() {
        return assessorScore;
    }

    public void setAssessorScore(Float assessorScore) {
        this.assessorScore = assessorScore;
    }

    public Float getAverage() {
        return average;
    }

    public void setAverage(Float average) {
        this.average = average;
    }
}
