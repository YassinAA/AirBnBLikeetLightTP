package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.form.AppUserAdminForm;
import com.tp.AirBnBLikeetLight.form.AppUserForm;

public interface AppUserRepositoryCustom {

    AppUser findUserAccountConnexion(String userName);

    AppUser saveAdmin(AppUserAdminForm appUserAdminForm);

    AppUser saveUser(AppUserForm appUserForm);
}
