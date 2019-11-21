package com.tp.AirBnBLikeetLight.service;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.repository.AppRoleRepository;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findUserAccountConnexion(userName);
        System.out.println("AppUser= " + user);

        if (user == null) {
            throw new UsernameNotFoundException("AppUser " //
                    + userName + " was not found in the database");
        }

        // [ROLE_LOCATAIRE, ROLE_ADMIN,..]//
        List<String> roleNames = this.appRoleRepository.getRoleNames(user.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_LOCATAIRE, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
                boolean enabled = user.isActive();
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonLocked = true;
            }
        }


        UserDetails userDetails = (UserDetails) new User(user.getUserName(), //
                user.getEncrytedPassword(), grantList);

        return userDetails;
    }

}