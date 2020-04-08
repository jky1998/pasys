package ntu.jky.form;

// 人员管理查询
public class StaffQueryForm {
    private String no;
    private int departmentId;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
