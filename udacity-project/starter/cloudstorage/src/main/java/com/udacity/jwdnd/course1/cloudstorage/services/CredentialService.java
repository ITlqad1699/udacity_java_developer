package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.CredentialDTO;
import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

import java.util.List;

/**
 * The interface Credential service.
 */
public interface CredentialService {
    /**
     * Gets all credential.
     *
     * @param userId the user id
     * @return the all credential
     */
    List<CredentialDTO> getAllCredential(Integer userId);
    
    /**
     * Persist credential persist response dto.
     *
     * @param credential the credential
     * @return the persist response dto
     */
    PersistResponseDTO persistCredential(Credential credential);
    
    /**
     * Delete credential string.
     *
     * @param integer the integer
     * @return the string
     */
    String deleteCredential(Integer integer);
}
