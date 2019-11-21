package com.tp.AirBnBLikeetLight.controller;

import java.util.List;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.Product;
import com.tp.AirBnBLikeetLight.form.ProductForm;
import com.tp.AirBnBLikeetLight.model.OrderDetailInfo;
import com.tp.AirBnBLikeetLight.model.OrderInfo;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import com.tp.AirBnBLikeetLight.repository.OrderDetailRepository;
import com.tp.AirBnBLikeetLight.repository.OrderRepository;
import com.tp.AirBnBLikeetLight.repository.ProductRepository;
import com.tp.AirBnBLikeetLight.validator.ProductFormValidator;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@Transactional
public class AdminController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductFormValidator productFormValidator;

    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == ProductForm.class) {
            dataBinder.setValidator(productFormValidator);
        }
    }

    // GET: Show Login Page
    @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
    public String login(Model model) {

        return "login";
    }

    @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
    public String accountInfo(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDetails.getPassword());
        System.out.println(userDetails.getUsername());
        System.out.println(userDetails.isEnabled());

        model.addAttribute("userDetails", userDetails);
        return "accountInfo";
    }

    @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
    public String orderList(HttpServletRequest request, Model model) {

        int page = 0; //default page number is 0 (yes it is weird)
        int size = 3; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("orders",  orderRepository.findAll(PageRequest.of(page, size)));

        return "orderList";

    }

    // GET: Show product.
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
    public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
        ProductForm productForm = null;

        if (code != null && code.length() > 0) {
            Product product = productRepository.findByCode(code);
            if (product != null) {
                productForm = new ProductForm(product);
            }
        }
        if (productForm == null) {
            productForm = new ProductForm();
            productForm.setNewProduct(true);
        }
        model.addAttribute("productForm", productForm);
        return "product";
    }

    // POST: Save product
    @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
    public String productSave(Model model, //
                              @ModelAttribute("productForm") @Validated ProductForm productForm, //
                              BindingResult result, //
                              final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "product";
        }
        try {
            productRepository.saveProduct(productForm);
        } catch (Exception e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            String message = rootCause.getMessage();
            model.addAttribute("errorMessage", message);
            // Show product form.
            return "product";
        }

        return "redirect:/productList";
    }

    @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
    public String orderView(Model model, @RequestParam("orderId") String orderId) {
        OrderInfo orderInfo = null;
        if (orderId != null) {
            orderInfo = orderRepository.getOrderInfo(orderId);
        }
        if (orderInfo == null) {
            return "redirect:/admin/orderList";
        }
        List<OrderDetailInfo> details = this.orderDetailRepository.listOrderDetailInfos(orderId);
        orderInfo.setDetails(details);

        model.addAttribute("orderInfo", orderInfo);

        return "order";
    }

    @RequestMapping(value = { "/admin/userList" }, method = RequestMethod.GET)
    public String userList(HttpServletRequest request, Model model) {

        int page = 0; //default page number is 0 (yes it is weird)
        int size = 4; //default page size is 10

        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }

        model.addAttribute("users",  appUserRepository.findAll(PageRequest.of(page, size)));

        return "userList";
    }

    @RequestMapping(value = { "/admin/user/{id}/delete" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable("id") long id) {

        // delete the user
        appUserRepository.deleteById(id);
        System.out.println(id);
        return "redirect:/admin/userList";
    }

    @RequestMapping(value = { "/admin/user/{id}/activate" }, method = RequestMethod.GET)
    public String activateUser(@PathVariable("id") long id) {

        AppUser appUser = appUserRepository.findById(id);
        appUser.setActive(true);
        return "redirect:/admin/userList";
    }

    @RequestMapping(value = { "/admin/user/{id}/disable" }, method = RequestMethod.GET)
    public String disableUser(@PathVariable("id") long id) {

        AppUser appUser = appUserRepository.findById(id);
        appUser.setActive(false);
        return "redirect:/admin/userList";
    }

    @RequestMapping(value = { "/user/rentalList" }, method = RequestMethod.GET)
    public String rentailList(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByUserName(userDetails.getUsername());

        model.addAttribute("rentals",  orderRepository.findAllByUser(appUser));

        return "rentailList";
    }

    @RequestMapping(value = { "/tenant/propertyList" }, method = RequestMethod.GET)
    public String propertyList(Model model) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByUserName(userDetails.getUsername());

        model.addAttribute("properties",  productRepository.findAllByUser(appUser));

        return "propertyList";
    }

    @RequestMapping(value = { "/admin/prduct/{id}/delete" }, method = RequestMethod.GET)
    public String deleteProduct(@PathVariable("id") long id) {

        // delete ptoduct
        productRepository.deleteById(id);
        return "redirect:/productList";
    }
}
