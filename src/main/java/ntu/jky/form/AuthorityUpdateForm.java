package ntu.jky.form;

/**
 * 权限设置 之 修改权限
 */
public class AuthorityUpdateForm {
    private Integer roleId;
    private Integer[] authorityIds;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer[] getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(Integer[] authorityIds) {
        this.authorityIds = authorityIds;
    }
}
