package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository  extends JpaRepository<AppUser, Long>, AppUserRepositoryCustom {

    public AppUser findByUserName(String userName);

    public AppUser findByEmail(String email);

    public AppUser findById(long id);
}
