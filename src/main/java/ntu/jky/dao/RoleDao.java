package ntu.jky.dao;

import ntu.jky.bean.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findAll();  // 查看所有role
}
