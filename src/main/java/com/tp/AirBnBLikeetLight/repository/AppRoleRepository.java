package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository  extends JpaRepository<AppRole, Long>, AppRoleRepositoryCustom {

    public AppRole findByRoleName(String name);
}
