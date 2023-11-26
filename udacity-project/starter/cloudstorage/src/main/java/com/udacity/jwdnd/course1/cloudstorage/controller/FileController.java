package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.dto.FileDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import static com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant.REDIRECT_HOME;

/**
 * Author: DaLQA
 * Date: 2023/19/11 1:27
 * Version: 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping(URLConstant.FILE)
public class FileController {
    /**
     * The File service.
     */
    FileService fileService;
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    /**
     * Upload string.
     *
     * @param multipartFile      the multipart file
     * @param redirectAttributes the redirect attributes
     * @return the string
     * @throws IOException the io exception
     */
    @PostMapping(URLConstant.UPLOAD)
    public String upload(@RequestParam(name = CommonConstant.FILE_UPLOAD) MultipartFile multipartFile,
                         RedirectAttributes redirectAttributes) throws IOException {
        
        if (StringUtils.hasText(validateFileEmpty(multipartFile))) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE, validateFileEmpty(multipartFile));
            return REDIRECT_HOME;
        }
        
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(multipartFile.getOriginalFilename());
        fileDTO.setFileSize(multipartFile.getSize());
        fileDTO.setFileData(multipartFile.getBytes());
        fileDTO.setContentType(multipartFile.getContentType());
        
        String message = fileService.uploadFile(fileDTO);
        
        if (StringUtils.hasText(message)) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE, message);
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    messageSource.getMessage(MessageConstant.UPLOAD_SUCCESS,
                            new String[]{fileDTO.getFileName()}, Locale.getDefault()));
        }
        
        return REDIRECT_HOME;
        
    }
    
    @GetMapping(URLConstant.DELETE_FILE)
    public String deleteFile(@PathVariable String fileId, RedirectAttributes redirectAttributes) {
        String message = fileService.deleteFile(Integer.valueOf(fileId));
        if (StringUtils.hasText(message)) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE, message);
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    messageSource.getMessage(MessageConstant.DELETE_FILE_SUCCESS, null, Locale.getDefault()));
        }
        return REDIRECT_HOME;
    }
    
    
    @GetMapping(URLConstant.GET_FILE_BY_ID)
    public ResponseEntity<byte[]> getFileByID(@PathVariable String fileId, RedirectAttributes redirectAttributes) {
        
        Optional<File> file = fileService.getFileByFileId(Integer.valueOf(fileId));
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE,
                    messageSource.getMessage(MessageConstant.FILE_NOT_EXIST, null, Locale.getDefault()));
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(file.get().getContentType()));
        return new ResponseEntity<>(file.get().getFileData(), headers, HttpStatus.OK);
    }
    
    private String validateFileEmpty(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        if (multipartFile.isEmpty()) {
            return messageSource.getMessage(MessageConstant.FILE_EMPTY, new String[]{fileName}, Locale.getDefault());
        }
        return StringUtils.EMPTY;
    }
    
    
}
