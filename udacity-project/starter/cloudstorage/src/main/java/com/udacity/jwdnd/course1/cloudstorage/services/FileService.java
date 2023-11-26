package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

import java.util.List;
import java.util.Optional;

/**
 * The interface File service.
 */
public interface FileService {
    /**
     * Gets all file.
     *
     * @param userId the user id
     * @return the all file
     */
    List<File> getAllFile(Integer userId);
    
    /**
     * Upload file string.
     *
     * @param file the file
     * @return the string
     */
    String uploadFile(FileDTO file);
    
    /**
     * Delete file string.
     *
     * @param fileId the file id
     * @return the string
     */
    String deleteFile(Integer fileId);
    
    /**
     * Gets file by file id.
     *
     * @param fileId the file id
     * @return the file by file id
     */
    Optional<File> getFileByFileId(Integer fileId);
}
