package ntu.jky.bean;

/**
 * 权限类
 */
public class Authority {
    private Integer id;     // 编号
    private String name;    // 权限名
    private String comment;// 权限说明

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
