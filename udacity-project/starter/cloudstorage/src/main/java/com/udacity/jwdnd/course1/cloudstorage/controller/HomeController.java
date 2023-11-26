package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.impl.EncryptionService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;
import java.util.Optional;

/**
 * Author: DaLQA
 * Date: 2023/11/18 23:09
 * Version: 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping(URLConstant.HOME)
public class HomeController {
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * The Note service.
     */
    NoteService noteService;
    
    /**
     * The Credential service.
     */
    CredentialService credentialService;
    
    /**
     * The Encryption service.
     */
    EncryptionService encryptionService;
    
    /**
     * The File service.
     */
    FileService fileService;
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    /**
     * Gets home page.
     *
     * @param model the model
     * @return the home page
     */
    @GetMapping()
    public String getHomePage(Model model) {
        Optional<User> user = userService.getUser();
        if (user.isEmpty()) {
            
            model.addAttribute(MessageConstant.ERROR_MESSAGE, messageSource.
                    getMessage(MessageConstant.TIMEOUT, null, Locale.getDefault()));
            return URLConstant.LOGIN;
        }
        Integer userId = user.get().getUserId();
        
        loader(userId, model);
        
        return URLConstant.HOME;
    }
    
    private void loader(Integer userId, Model model) {
        model.addAttribute("credentials", credentialService.getAllCredential(userId));
        model.addAttribute("notes", noteService.getAllNote(userId));
        model.addAttribute("files", fileService.getAllFile(userId));
//        model.addAttribute("encryptionService", encryptionService);
    }
    
}
