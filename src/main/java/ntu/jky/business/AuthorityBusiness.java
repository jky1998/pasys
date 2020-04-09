package ntu.jky.business;

import ntu.jky.bean.Authority;
import ntu.jky.bean.RoleAuthorityRelation;
import ntu.jky.service.RoleAuthorityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorityBusiness {
    @Autowired
    private RoleAuthorityRelationService relationService;

    public List<Authority> getAuthorities(Integer id) {
        List<RoleAuthorityRelation> relations = relationService.findByRoleId(id);
        List<Authority> authorities = new ArrayList<>();
        if (relations.size() != 0) {
            for (RoleAuthorityRelation relation : relations) {
                Authority authority = relation.getAuthority();
                authorities.add(authority);
            }
        }
        return  authorities;
    }
}
