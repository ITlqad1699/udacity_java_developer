package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.controller.vm.CredentialVM;
import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.services.impl.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant.REDIRECT_HOME;

/**
 * The type Credential controller.
 */
@Controller
@AllArgsConstructor
@RequestMapping(URLConstant.CREDENTIAL)
public class CredentialController {
    
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * The Credential service.
     */
    CredentialService credentialService;
    
    /**
     * The Model mapper.
     */
    ModelMapper modelMapper;
    
    /**
     * The Encryption service.
     */
    EncryptionService encryptionService;
    
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    
    /**
     * Persist credential string.
     *
     * @param redirectAttributes the redirect attributes
     * @param credentialVM       the credential vm
     * @return the string
     */
    @PostMapping(URLConstant.ADD_OR_UPDATE)
    public String persistCredential(RedirectAttributes redirectAttributes, @ModelAttribute CredentialVM credentialVM) {
        redirectAttributes.addFlashAttribute(CommonConstant.TAB, String.format(CommonConstant.NAV_TAB, "credentials"));
        Credential credential = modelMapper.map(credentialVM, Credential.class);
        
        PersistResponseDTO response = credentialService.persistCredential(credential);
        if (response.isFailed()) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE,
                    response.getMessage());
            
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    response.getMessage());
        }
        
        return REDIRECT_HOME;
    }
    
    
    /**
     * Delete credential string.
     *
     * @param credentialId       the credential id
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @GetMapping(URLConstant.DELETE_CREDENTIAL)
    public String deleteCredential(@PathVariable String credentialId, RedirectAttributes redirectAttributes) {
        String message = credentialService.deleteCredential(Integer.valueOf(credentialId));
        redirectAttributes.addFlashAttribute(CommonConstant.TAB, String.format(CommonConstant.NAV_TAB, "credentials"));
        if (StringUtils.hasText(message)) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE, message);
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    messageSource.getMessage(MessageConstant.DELETE_CREDENTIAL_SUCCESS, null, Locale.getDefault()));
        }
        return REDIRECT_HOME;
    }
    
    
}
