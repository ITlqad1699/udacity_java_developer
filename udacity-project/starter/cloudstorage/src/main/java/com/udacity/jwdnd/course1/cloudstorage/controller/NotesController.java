package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant;
import com.udacity.jwdnd.course1.cloudstorage.controller.vm.NoteVM;
import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

import static com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant.NOTE;
import static com.udacity.jwdnd.course1.cloudstorage.constant.URLConstant.REDIRECT_HOME;

/**
 * Author: DaLQA
 * Date: 2023/11/22 23:00
 * Version: 1.0
 */
@Controller
@AllArgsConstructor
@RequestMapping(NOTE)
public class NotesController {
    /**
     * The Note service.
     */
    NoteService noteService;
    
    /**
     * The Model mapper.
     */
    ModelMapper modelMapper;
    
    /**
     * The Message source.
     */
    MessageSource messageSource;
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * Add new or update note string.
     *
     * @param redirectAttributes the redirect attributes
     * @param noteVM             the note vm
     * @return the string
     */
    @PostMapping(URLConstant.ADD_OR_UPDATE)
    public String persistNote(RedirectAttributes redirectAttributes, @ModelAttribute NoteVM noteVM) {
        
        redirectAttributes.addFlashAttribute(CommonConstant.TAB, String.format(CommonConstant.NAV_TAB, "notes"));
        Note note = modelMapper.map(noteVM, Note.class);
        PersistResponseDTO response = noteService.persistNote(note);
        if (response.isFailed()) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE,
                    response.getMessage());
            
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    response.getMessage());
        }
        
        return REDIRECT_HOME;
    }
    
    /**
     * Delete file string.
     *
     * @param noteId             the note id
     * @param redirectAttributes the redirect attributes
     * @return the string
     */
    @GetMapping(URLConstant.DELETE_NOTE)
    public String deleteFile(@PathVariable String noteId, RedirectAttributes redirectAttributes) {
        String message = noteService.deleteNote(Integer.valueOf(noteId));
        redirectAttributes.addFlashAttribute(CommonConstant.TAB, String.format(CommonConstant.NAV_TAB, "notes"));
        if (StringUtils.hasText(message)) {
            redirectAttributes.addFlashAttribute(MessageConstant.ERROR_MESSAGE, message);
        } else {
            redirectAttributes.addFlashAttribute(MessageConstant.SUCCESS_MESSAGE,
                    messageSource.getMessage(MessageConstant.DELETE_NOTE_SUCCESS, null, Locale.getDefault()));
        }
        return REDIRECT_HOME;
    }
}
