package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Author: DaLQA
 * Date: 2023/25/11
 * Version: 1.0
 */
@Getter
@Setter
public class PersistResponseDTO {
    private String message;
    private boolean isFailed;
    
    public PersistResponseDTO() {
        this.isFailed = false;
    }
}
