package ntu.jky.form;

// 责任分解
public class ResponseBreakdownForm {
    private Integer[] planIds;
    private Integer[] staffIds;

    public Integer[] getPlanIds() {
        return planIds;
    }

    public void setPlanIds(Integer[] planIds) {
        this.planIds = planIds;
    }

    public Integer[] getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(Integer[] staffIds) {
        this.staffIds = staffIds;
    }
}
