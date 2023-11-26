package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.SQLConstant;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface Credential mapper.
 */
@Mapper
public interface CredentialMapper {
    
    /**
     * Insert new.
     *
     * @param credential the credential
     */
    @Insert(SQLConstant.InsertSQL.INSERT_CREDENTIAL)
    @Options(useGeneratedKeys = true, keyProperty = CommonConstant.CREDENTIAL_ID)
    void insertNew(Credential credential);
    
    /**
     * Delete credential.
     *
     * @param credentialId the credential id
     */
    @Delete(SQLConstant.DeleteSQL.DELETE_CREDENTIAL)
    void deleteCredential(Integer credentialId);
    
    /**
     * Update credential.
     *
     * @param credentialId the credential id
     * @param newUserName  the new user name
     * @param url          the url
     * @param key          the key
     * @param password     the password
     */
    @Update(SQLConstant.UpdateSQL.UPDATE_CREDENTIAL)
    void updateCredential(Integer credentialId, String newUserName, String url, String key, String password);
    
    /**
     * Gets credential by user id.
     *
     * @param userId the user id
     * @return the credential by user id
     */
    @Select(SQLConstant.SelectSQL.SELECT_CREDENTIAL_BY_USER_ID)
    List<Credential> getCredentialByUserId(Integer userId);
    
    @Select(SQLConstant.SelectSQL.SELECT_CREDENTIAL_BY_CREDENTIAL_ID)
    Credential getCredentialByCredentialId(Integer credentialId);
}
