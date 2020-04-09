package ntu.jky.dao;

import ntu.jky.bean.RoleAuthorityRelation;

import java.util.List;

public interface RoleAuthorityRelationDao {
    List<RoleAuthorityRelation> findAll(RoleAuthorityRelation relation);
}
