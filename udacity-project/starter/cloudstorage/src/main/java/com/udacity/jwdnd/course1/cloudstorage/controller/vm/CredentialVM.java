package com.udacity.jwdnd.course1.cloudstorage.controller.vm;

import lombok.Getter;
import lombok.Setter;

/**
 * The type Credential vm.
 */
@Getter
@Setter
public class CredentialVM {
    private Integer credentialId;
    private String url;
    private String username;
    private String password;
}
