package com.tp.AirBnBLikeetLight.repositoryImpl;

import com.tp.AirBnBLikeetLight.entity.AppRole;
import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.UserRole;
import com.tp.AirBnBLikeetLight.form.AppUserAdminForm;
import com.tp.AirBnBLikeetLight.form.AppUserForm;
import com.tp.AirBnBLikeetLight.repository.AppRoleRepository;
import com.tp.AirBnBLikeetLight.repository.AppUserRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Transactional
public class AppUserRepositoryImpl implements AppUserRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Override
    public AppUser findUserAccountConnexion(String userName) {
        try {
            String sql = "Select e from " + AppUser.class.getName() + " e " //
                    + " Where e.userName = :userName AND e.active = true ";

            Query query = entityManager.createQuery(sql, AppUser.class);
            query.setParameter("userName", userName);

            return (AppUser) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public AppUser saveAdmin(AppUserAdminForm appUserAdminForm) {
        AppUser appUser = new AppUser();

        appUser.setEmail(appUserAdminForm.getEmail());
        String encrytedPassword = this.passwordEncoder.encode(appUserAdminForm.getEncrytedPassword());
        appUser.setEncrytedPassword(encrytedPassword);
        appUser.setFirstName(appUserAdminForm.getFirstName());
        appUser.setLastName(appUserAdminForm.getLastName());
        appUser.setUserName(appUserAdminForm.getUserName());
        appUser.setActive(true);

        this.entityManager.persist(appUser);

        UserRole userRole = new UserRole();
        userRole.setAppUser(appUser);

        AppRole appRole = appRoleRepository.findByRoleName("ROLE_ADMIN");
        userRole.setAppRole(appRole);

        UserRole userRole1 = new UserRole();
        userRole1.setAppUser(appUser);

        AppRole appRole1 = appRoleRepository.findByRoleName("ROLE_TENANT");
        userRole1.setAppRole(appRole1);

        UserRole userRole2 = new UserRole();
        userRole2.setAppUser(appUser);

        AppRole appRole2 = appRoleRepository.findByRoleName("ROLE_RENTER");
        userRole2.setAppRole(appRole2);

        this.entityManager.persist(userRole);
        this.entityManager.persist(userRole1);
        this.entityManager.persist(userRole2);

        this.entityManager.flush();

        return appUser;
    }

    @Transactional(rollbackFor = Exception.class)
    public AppUser saveUser(AppUserForm appUserForm) {

        AppUser appUser = new AppUser();

        appUser.setEmail(appUserForm.getEmail());
        String encrytedPassword = this.passwordEncoder.encode(appUserForm.getEncrytedPassword());
        appUser.setEncrytedPassword(encrytedPassword);
        appUser.setFirstName(appUserForm.getFirstName());
        appUser.setLastName(appUserForm.getLastName());
        appUser.setUserName(appUserForm.getUserName());
        appUser.setActive(false);

        this.entityManager.persist(appUser);

        if (appUserForm.getTenant() == true){

            UserRole userRole1 = new UserRole();
            userRole1.setAppUser(appUser);

            AppRole appRole1 = appRoleRepository.findByRoleName("ROLE_TENANT");


            userRole1.setAppRole(appRole1);

            this.entityManager.persist(userRole1);
        }

        if (appUserForm.getRenter() == true){

            UserRole userRole2 = new UserRole();
            userRole2.setAppUser(appUser);

            AppRole appRole2 = appRoleRepository.findByRoleName("ROLE_RENTER");
            userRole2.setAppRole(appRole2);

            this.entityManager.persist(userRole2);
        }

        // Flush
        this.entityManager.flush();
        return appUser;
    }
}
