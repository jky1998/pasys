package ntu.jky.authority;

import ntu.jky.bean.Authority;

import java.util.List;

public class LoginStaff {
    private Integer id;          // 用户编号
    private String name;         // 用户名
    private Integer roleId;           // 用户角色
    private List<Authority> authorities;  // 用户权限

    private static LoginStaff instance = new LoginStaff();

    private LoginStaff() {}

    public static LoginStaff getInstance() {
        return instance;
    }

//    public void setInstance(Integer id, String name, Integer roleId, List<Authority> authorities) {
//        this.id = id;
//        this.name = name;
//        this.roleId = roleId;
//        this.authorities = authorities;
//    }

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
