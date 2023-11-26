package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type File.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private int fileId;
    private String fileName;
    private String contentType;
    private Long fileSize;
    private Integer userId;
    private byte[] fileData;
}
