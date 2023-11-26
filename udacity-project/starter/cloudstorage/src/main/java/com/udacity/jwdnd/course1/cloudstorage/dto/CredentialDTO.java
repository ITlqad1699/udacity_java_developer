package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: DaLQA
 * @Date: 2023-11-26 09:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDTO {
    
    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private String passwordDecrypt;
    private Integer userid;
}
