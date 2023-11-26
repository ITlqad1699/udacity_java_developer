package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.SQLConstant;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * The interface Note mapper.
 */
@Mapper
public interface NoteMapper {
    /**
     * Gets all note by user id.
     *
     * @param userId the user id
     * @return the all note by user id
     */
    @Select(SQLConstant.SelectSQL.SELECT_NOTE_BY_USER_ID)
    List<Note> getAllNoteByUserId(Integer userId);
    
    /**
     * Insert.
     *
     * @param note the note
     */
    @Insert(SQLConstant.InsertSQL.INSERT_NOTE)
    @Options(useGeneratedKeys = true, keyProperty = CommonConstant.NOTE_ID)
    void insert(Note note);
    
    /**
     * Update.
     *
     * @param noteId          the note id
     * @param noteDescription the description
     * @param noteTitle       the title
     */
    @Update(SQLConstant.UpdateSQL.UPDATE_NOTE)
    void update(Integer noteId, String noteDescription, String noteTitle);
    
    /**
     * Gets note by note id.
     *
     * @param noteId the note id
     * @return the note by note id
     */
    @Select(SQLConstant.SelectSQL.SELECT_NOTE_BY_NOTE_ID)
    Note getNoteByNoteId(Integer noteId);
    
    /**
     * Delete note.
     *
     * @param noteId the note id
     */
    @Delete(SQLConstant.DeleteSQL.DELETE_NOTE)
    void deleteNote(Integer noteId);
}
