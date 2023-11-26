package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.dto.PersistResponseDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

import java.util.List;

/**
 * The interface Note service.
 */
public interface NoteService {
    /**
     * Gets all note.
     *
     * @param userId the user id
     * @return the all note
     */
    List<Note> getAllNote(Integer userId);
    
    /**
     * Add new or update note note persist response dto.
     *
     * @param note the note
     * @return the note persist response dto
     */
    PersistResponseDTO persistNote(Note note);
    
    /**
     * Delete note string.
     *
     * @param integer the integer
     * @return the string
     */
    String deleteNote(Integer integer);
}
