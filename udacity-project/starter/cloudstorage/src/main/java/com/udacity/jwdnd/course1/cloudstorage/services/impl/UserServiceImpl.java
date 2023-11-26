package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * The type User service.
 */
@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    /**
     * The User mapper.
     */
    UserMapper userMapper;
    /**
     * The Hash service.
     */
    HashService hashService;
    
    @Override
    public String logout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (ObjectUtils.isEmpty(authentication)) {
                return messageSource.getMessage(MessageConstant.RESPONSE_LOGOUT, null, Locale.getDefault());
            }
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return messageSource.getMessage(MessageConstant.RESPONSE_LOGOUT, null, Locale.getDefault());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    
    @Override
    public String signup(User newUser, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userMapper.getUserByUserName(newUser.getUsername());
            
            if (Objects.nonNull(user)) {
                model.addAttribute(MessageConstant.ERROR_MESSAGE, messageSource.
                        getMessage(MessageConstant.USER_ALREADY_EXIST, new String[]{user.getUsername()}, Locale.getDefault()));
                return URLConstant.SIGNUP;
            }
            
            if (createNewUser(newUser) < 0) {
                model.addAttribute(MessageConstant.ERROR_MESSAGE, messageSource.
                        getMessage(MessageConstant.SIGNING_ERROR, null, Locale.getDefault()));
                return URLConstant.SIGNUP;
            }
            
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE, messageSource.
                    getMessage(MessageConstant.SIGNING_SUCCESS, null, Locale.getDefault()));
            
            return URLConstant.REDIRECT_LOGIN;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    
    @Override
    public Optional<User> getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            return Optional.ofNullable(userMapper.getUserByUserName(userName));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
    
    private int createNewUser(User user) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            secureRandom.nextBytes(salt);
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String hashedPassword = this.hashService.getHashedValue(user.getPassword(), encodedSalt);
            return userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword,
                    user.getFirstName(), user.getLastName()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }
}
