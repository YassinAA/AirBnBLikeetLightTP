package com.tp.AirBnBLikeetLight.controller;

import com.tp.AirBnBLikeetLight.form.AppUserAdminForm;
import com.tp.AirBnBLikeetLight.form.AppUserForm;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import com.tp.AirBnBLikeetLight.validator.AppUserAdminValidator;
import com.tp.AirBnBLikeetLight.validator.AppUserValidator;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class RegisterController {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserValidator appUserValidator;

    @Autowired
    private AppUserAdminValidator appUserAdminValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == AppUserForm.class) {
            dataBinder.setValidator(appUserValidator);
        }
        if (target.getClass() == AppUserAdminForm.class) {
            dataBinder.setValidator(appUserAdminValidator);
        }
    }


    // Show Register page.
    @RequestMapping(value = "/registerAdmin", method = RequestMethod.GET)
    public String viewRegisterAdmin(Model model) {

        AppUserAdminForm form = new AppUserAdminForm();

        model.addAttribute("appUserAdminForm", form);

        return "registerAdmin";
    }

    // POST: Save Admin
    @RequestMapping(value = { "/registerAdmin" }, method = RequestMethod.POST)
    public String adminSave(Model model, //
                            @ModelAttribute("appUserAdminForm") @Validated AppUserAdminForm appUserAdminForm, //
                            BindingResult result, //
                            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "registerAdmin";
        }
        try {
            appUserRepository.saveAdmin(appUserAdminForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "registerAdmin";
        }

        return "redirect:/";
    }

    // Show Register page.
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String viewRegister(Model model) {

        AppUserForm form = new AppUserForm();

        model.addAttribute("appUserForm", form);

        return "registerUser";
    }

    // POST: Save Tenant or Renter
    @RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String saveUser(Model model, //
                            @ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, //
                            BindingResult result, //
                            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "registerUser";
        }
        try {
            appUserRepository.saveUser(appUserForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);

            return "registerUser";
        }

        return "redirect:/";
    }
}
