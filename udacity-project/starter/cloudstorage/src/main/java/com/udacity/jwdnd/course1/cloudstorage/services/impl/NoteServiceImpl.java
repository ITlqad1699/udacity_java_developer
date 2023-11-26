package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.constant.MessageConstant;
import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * The type Note service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {
    /**
     * The Note mapper.
     */
    NoteMapper noteMapper;
    
    /**
     * The User service.
     */
    UserService userService;
    
    /**
     * The Message source.
     */
    MessageSource messageSource;
    
    
    @Override
    public List<Note> getAllNote(Integer userId) {
        return noteMapper.getAllNoteByUserId(userId);
    }
    
    @Override
    public PersistResponseDTO persistNote(Note note) {
        
        PersistResponseDTO response = new PersistResponseDTO();
        try {
            Optional<User> user = userService.getUser();
            if (user.isPresent()) {
                note.setUserId(user.get().getUserId());
                if (ObjectUtils.isEmpty(note.getNoteId())) {
                    noteMapper.insert(note);
                    response.setMessage(messageSource.getMessage(MessageConstant.INSERT_NEW_NOTE,
                            new String[]{note.getNoteTitle()}, Locale.getDefault()));
                } else {
                    response.setMessage(messageSource.getMessage(MessageConstant.UPDATE_NOTE,
                            new String[]{note.getNoteTitle()}, Locale.getDefault()));
                    noteMapper.update(note.getNoteId(), note.getNoteDescription(), note.getNoteTitle());
                }
            }
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setFailed(true);
            if (ObjectUtils.isEmpty(note.getNoteId())) {
                response.setMessage(messageSource.getMessage(MessageConstant.ADD_NOTE_FAILED,
                        null, Locale.getDefault()));
            }
            response.setMessage(messageSource.getMessage(MessageConstant.UPDATE_NOTE_FAILED,
                    null, Locale.getDefault()));
            return response;
        }
    }
    
    @Override
    public String deleteNote(Integer noteId) {
        try {
            Optional<Note> note = Optional.ofNullable(noteMapper.getNoteByNoteId(noteId));
            if (note.isEmpty()) {
                return messageSource.getMessage(MessageConstant.NOTE_NOT_EXIST, null, Locale.getDefault());
            }
            noteMapper.deleteNote(noteId);
            return StringUtils.EMPTY;
        } catch (Exception e) {
            log.error(e.getMessage());
            return messageSource.getMessage(MessageConstant.DELETE_FILE_FAIL, null, Locale.getDefault());
        }
    }
}
