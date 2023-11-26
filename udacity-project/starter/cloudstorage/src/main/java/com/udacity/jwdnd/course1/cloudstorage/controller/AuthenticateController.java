package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.controller.vm.UserVM;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type User controller.
 */
@Controller
@AllArgsConstructor
public class AuthenticateController {
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * The Model mapper.
     */
    ModelMapper modelMapper;
    
    /**
     * Login string.
     *
     * @return the string
     */
    @GetMapping(value = URLConstant.LOGIN)
    public String login() {
        return URLConstant.LOGIN;
    }
    
    /**
     * Logout string.
     *
     * @param request  the request
     * @param response the response
     * @return the string
     */
    @PostMapping(value = URLConstant.LOGOUT)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.logout(authentication, request, response);
    }
    
    /**
     * Signup view string.
     *
     * @return the string
     */
    @GetMapping(value = URLConstant.SIGNUP)
    public String signupView() {
        return URLConstant.SIGNUP;
    }
    
    /**
     * Signup string.
     *
     * @param userVM             the user vm
     * @param model              the model
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @PostMapping(value = URLConstant.SIGNUP)
    public String signup(@ModelAttribute UserVM userVM, Model model, RedirectAttributes redirectAttributes) {
        User user = modelMapper.map(userVM, User.class);
        return userService.signup(user, model, redirectAttributes);
    }
}
