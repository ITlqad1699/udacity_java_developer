package com.udacity.jwdnd.course1.cloudstorage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type File vm.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {
    private String fileName;
    
    private Long fileSize;
    
    private byte[] fileData;
    
    private String contentType;
}
