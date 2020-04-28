package ntu.jky.authority;

import ntu.jky.bean.Authority;

import java.util.List;

public class LoginStaff {
    private Integer id;          // 用户编号
    private String name;         // 用户名
    private Integer departmentId;
    private Integer roleId;           // 用户角色
    private List<Authority> authorities;  // 用户权限

    private static LoginStaff instance = new LoginStaff();

    private LoginStaff() {}

    public static LoginStaff getInstance() {
        return instance;
    }

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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public static void setInstance(LoginStaff instance) {
        LoginStaff.instance = instance;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
