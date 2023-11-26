package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Credential service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CredentialServiceImpl implements CredentialService {
    /**
     * The Credential mapper.
     */
    CredentialMapper credentialMapper;
    
    /**
     * The Encryption service.
     */
    EncryptionService encryptionService;
    
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    /**
     * The Model mapper.
     */
    ModelMapper modelMapper;
    
    @Override
    public List<CredentialDTO> getAllCredential(Integer userId) {
        List<Credential> credentials = credentialMapper.getCredentialByUserId(userId);
        List<CredentialDTO> credentialDTOS = credentials.stream()
                .map(credential -> modelMapper.map(credential, CredentialDTO.class))
                .collect(Collectors.toList());
        
        credentialDTOS.forEach(credentialDTO -> {
            credentialDTO.setPasswordDecrypt(encryptionService.decryptValue(credentialDTO.getPassword(), credentialDTO.getKey()));
        });
        return credentialDTOS;
    }
    
    @Override
    public PersistResponseDTO persistCredential(Credential credential) {
        PersistResponseDTO response = new PersistResponseDTO();
        try {
            Optional<User> user = userService.getUser();
            String key = encryptionService.generateKey();
            String password = encryptionService.encryptValue(credential.getPassword(), key);
            if (user.isPresent()) {
                credential.setKey(key);
                credential.setUserid(user.get().getUserId());
                credential.setPassword(password);
                if (ObjectUtils.isEmpty(credential.getCredentialId())) {
                    credentialMapper.insertNew(credential);
                    response.setMessage(messageSource.getMessage(MessageConstant.INSERT_CREDENTIAL,
                            new String[]{credential.getUrl()}, Locale.getDefault()));
                } else {
                    response.setMessage(messageSource.getMessage(MessageConstant.UPDATE_CREDENTIAL,
                            new String[]{credential.getUrl()}, Locale.getDefault()));
                    credentialMapper.updateCredential(credential.getCredentialId(), credential.getUsername(), credential.getUrl()
                            , credential.getKey(), credential.getPassword());
                }
            }
            
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setFailed(true);
            if (ObjectUtils.isEmpty(credential.getCredentialId())) {
                response.setMessage(messageSource.getMessage(MessageConstant.INSERT_CREDENTIAL_FAILED,
                        null, Locale.getDefault()));
            }
            response.setMessage(messageSource.getMessage(MessageConstant.UPDATE_CREDENTIAL_FAILED,
                    null, Locale.getDefault()));
            return response;
        }
        return response;
    }
    
    @Override
    public String deleteCredential(Integer credentialId) {
        try {
            Optional<Credential> credential = Optional.ofNullable(credentialMapper.getCredentialByCredentialId(credentialId));
            if (credential.isEmpty()) {
                return messageSource.getMessage(MessageConstant.CREDENTIAL_NOT_EXIST, null, Locale.getDefault());
            }
            credentialMapper.deleteCredential(credentialId);
            return StringUtils.EMPTY;
        } catch (Exception e) {
            log.error(e.getMessage());
            return messageSource.getMessage(MessageConstant.DELETE_CREDENTIAL_FAILED, null, Locale.getDefault());
        }
    }
}
