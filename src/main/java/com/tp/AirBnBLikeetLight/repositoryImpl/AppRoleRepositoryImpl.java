package com.tp.AirBnBLikeetLight.repositoryImpl;

import com.tp.AirBnBLikeetLight.entity.UserRole;
import com.tp.AirBnBLikeetLight.repository.AppRoleRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


@Transactional
public class AppRoleRepositoryImpl implements AppRoleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<String> getRoleNames(Long userId) {
        String sql = "Select ur.appRole.roleName from " + UserRole.class.getName() + " ur " //
                + " where ur.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
