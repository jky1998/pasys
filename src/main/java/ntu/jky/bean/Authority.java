package ntu.jky.bean;

/**
 * 权限类
 */
public class Authority {
    private Integer id;     // 编号
    private String name;    // 权限

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
}
