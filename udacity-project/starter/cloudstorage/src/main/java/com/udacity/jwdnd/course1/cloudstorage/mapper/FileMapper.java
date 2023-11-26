package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.SQLConstant;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface File mapper.
 */
@Mapper
public interface FileMapper {
    /**
     * Gets file by user id.
     *
     * @param userId the user id
     * @return the file by user id
     */
    @Select(SQLConstant.SelectSQL.SELECT_FILE_BY_USER_ID)
    List<File> getFileByUserId(Integer userId);
    
    /**
     * Gets file by file name.
     *
     * @param originalFilename the original filename
     * @return the file by file name
     */
    @Select(SQLConstant.SelectSQL.SELECT_FILE_BY_FILE_NAME)
    File getFileByFileName(String originalFilename);
    
    /**
     * Insert.
     *
     * @param file the file
     * @return the int
     */
    @Insert(SQLConstant.InsertSQL.INSERT_FILE)
    @Options(useGeneratedKeys = true, keyProperty = CommonConstant.FILE_ID)
    int insert(File file);
    
    /**
     * Delete file.
     *
     * @param fileId the file id
     */
    @Delete(SQLConstant.DeleteSQL.DELETE_FILE)
    void deleteFile(int fileId);
    
    /**
     * Gets file by file id.
     *
     * @param fileId the file id
     * @return the file by file id
     */
    @Select(SQLConstant.SelectSQL.SELECT_FILE_BY_FILE_ID)
    File getFileByFileId(Integer fileId);
}