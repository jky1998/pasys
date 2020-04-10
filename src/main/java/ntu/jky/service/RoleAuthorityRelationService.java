package ntu.jky.service;

import ntu.jky.bean.Role;
import ntu.jky.bean.RoleAuthorityRelation;
import ntu.jky.dao.RoleAuthorityRelationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleAuthorityRelationService {
    @Autowired
    private RoleAuthorityRelationDao relationDao;

    public List<RoleAuthorityRelation> findByRoleId(Integer id) {
        Role role = new Role();
        role.setId(id);
        RoleAuthorityRelation relation = new RoleAuthorityRelation();
        relation.setRole(role);
        List<RoleAuthorityRelation> relations = relationDao.findAll(relation);
        return relations;
    }

    public void add(RoleAuthorityRelation relation) {
        relationDao.add(relation);
    }

    public void delete(List<Integer> ids) {
        relationDao.delete(ids);
    }
}
