package ntu.jky.business;

import ntu.jky.bean.Authority;
import ntu.jky.bean.Role;
import ntu.jky.bean.RoleAuthorityRelation;
import ntu.jky.form.AuthorityUpdateForm;
import ntu.jky.service.RoleAuthorityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleAuthorityRelationBusiness {
    @Autowired
    private RoleAuthorityRelationService relationService;

    // 修改角色权限关联
    public void updateRelations(AuthorityUpdateForm form) {
        Integer roleId = form.getRoleId();
        Integer[] authorityIds = form.getAuthorityIds();
        List<RoleAuthorityRelation> existRelations = relationService.findByRoleId(roleId);
        if (authorityIds != null) {
            for (int i = 0; i < authorityIds.length; i++) {
                boolean flag = false;
                int aId = authorityIds[i];
                if (existRelations != null) {
                    for (RoleAuthorityRelation relation : existRelations) {
                        int existAId = relation.getAuthority().getId();
                        // relation 已存在，不操作
                        if (aId == existAId) {
                            flag = true;
                            existRelations.remove(relation);
                            break;
                        }
                    }
                }
                // list中不存在页面返回的权限 添加relation
                if (!flag) {
                    add(roleId, aId);
                }
            }
        }
        if (existRelations.size() != 0) {
            delete(existRelations);
        }
    }

    // 添加角色权限关联
    public void add(Integer roleId, Integer authorityId) {
        RoleAuthorityRelation r = new RoleAuthorityRelation();
        Role role = new Role();
        role.setId(roleId);
        Authority authority = new Authority();
        authority.setId(authorityId);
        r.setRole(role);
        r.setAuthority(authority);
        relationService.add(r);
    }

    // 删除角色权限关联
    public void delete(List<RoleAuthorityRelation> relations) {
        List<Integer> ids = new ArrayList<>();
        for (RoleAuthorityRelation relation : relations) {
            Integer id = relation.getId();
            ids.add(id);
        }
        relationService.delete(ids);
    }
}
