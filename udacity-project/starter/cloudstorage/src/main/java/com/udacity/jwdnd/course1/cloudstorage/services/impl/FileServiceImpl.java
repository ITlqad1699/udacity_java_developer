package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The type File service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    /**
     * The File mapper.
     */
    FileMapper fileMapper;
    
    /**
     * The User mapper.
     */
    UserMapper userMapper;
    
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    /**
     * The Model mapper.
     */
    ModelMapper modelMapper;
    
    /**
     * The User service.
     */
    UserService userService;
    
    @Override
    public List<File> getAllFile(Integer userId) {
        return fileMapper.getFileByUserId(userId);
    }
    
    @Override
    public String uploadFile(FileDTO fileDTO) {
        try {
            Optional<User> user = userService.getUser();
            if (user.isPresent()) {
                String fileName = fileDTO.getFileName();
                
                if (StringUtils.hasText(validateFileExist(fileName))) {
                    return messageSource.getMessage(MessageConstant.FILE_EXISTS, new String[]{fileName}, Locale.getDefault());
                }
                
                File file = modelMapper.map(fileDTO, File.class);
                
                file.setUserId(userMapper.getUserByUserName(user.get().getUsername()).getUserId());
                
                fileMapper.insert(file);
                return StringUtils.EMPTY;
            }
            return messageSource.getMessage(MessageConstant.UPLOAD_FAIL, new String[]{fileDTO.getFileName()}, Locale.getDefault());
        } catch (Exception e) {
            log.error(e.getMessage());
            return messageSource.getMessage(MessageConstant.UPLOAD_FAIL, new String[]{fileDTO.getFileName()}, Locale.getDefault());
        }
    }
    
    @Override
    public String deleteFile(Integer fileId) {
        try {
            Optional<File> file = Optional.ofNullable(fileMapper.getFileByFileId(fileId));
            if (file.isEmpty()) {
                return messageSource.getMessage(MessageConstant.FILE_NOT_EXIST, null, Locale.getDefault());
            }
            fileMapper.deleteFile(fileId);
            return StringUtils.EMPTY;
        } catch (Exception e) {
            log.error(e.getMessage());
            return messageSource.getMessage(MessageConstant.DELETE_FILE_FAIL, null, Locale.getDefault());
        }
    }
    
    @Override
    public Optional<File> getFileByFileId(Integer fileId) {
        return Optional.ofNullable(fileMapper.getFileByFileId(fileId));
    }
    
    private String validateFileExist(String fileName) {
        Optional<File> file = Optional.ofNullable(fileMapper.getFileByFileName(fileName));
        if (file.isPresent()) {
            return messageSource.getMessage(MessageConstant.FILE_EXISTS, new String[]{fileName}, Locale.getDefault());
        }
        return StringUtils.EMPTY;
    }
    
}
