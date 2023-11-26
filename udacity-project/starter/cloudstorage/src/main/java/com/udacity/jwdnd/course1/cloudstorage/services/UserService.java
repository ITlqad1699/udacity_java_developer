package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Logout string.
     *
     * @param authentication the authentication
     * @param request        the request
     * @param response       the response
     * @return the string
     */
    String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response);
    
    /**
     * Signup string.
     *
     * @param user               the user
     * @param model              the model
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    String signup(User user, Model model, RedirectAttributes redirectAttributes);
    
    /**
     * Gets user.
     *
     * @return the user
     */
    Optional<User> getUser();
}
