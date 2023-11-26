package com.udacity.jwdnd.course1.cloudstorage.constant;

/**
 * The type Sql constant.
 */
public class SQLConstant {
    
    /**
     * The type Select sql.
     */
    public class SelectSQL {
        /**
         * The constant SELECT_USER_BY_USER_NAME.
         */
        public static final String SELECT_USER_BY_USER_NAME = "SELECT * FROM USERS WHERE username = #{username}";
        
        /**
         * The constant SELECT_USER_BY_USER_ID.
         */
        public static final String SELECT_USER_BY_USER_ID = "SELECT * FROM USERS WHERE userid = #{userId}";
        
        /**
         * The constant SELECT_NOTE_BY_USER_ID.
         */
        public static final String SELECT_NOTE_BY_USER_ID = "SELECT * FROM NOTES WHERE userid = #{userId}";
        
        /**
         * The constant SELECT_FILE_BY_FILE_ID.
         */
        public static final String SELECT_FILE_BY_FILE_ID = "SELECT * FROM FILES WHERE fileid = #{fileId}";
        
        /**
         * The constant SELECT_FILE_BY_USER_ID.
         */
        public static final String SELECT_FILE_BY_USER_ID = "SELECT * FROM FILES WHERE userid = #{userId}";
        
        /**
         * The constant SELECT_FILE_BY_FILE_NAME.
         */
        public static final String SELECT_FILE_BY_FILE_NAME = "SELECT * FROM FILES WHERE filename = #{fileName}";
        
        /**
         * The constant SELECT_CREDENTIAL_BY_USER_ID.
         */
        public static final String SELECT_CREDENTIAL_BY_USER_ID = "SELECT * FROM CREDENTIALS WHERE userid = #{userId}";
        /**
         * The constant SELECT_NOTE_BY_NOTE_ID.
         */
        public static final String SELECT_NOTE_BY_NOTE_ID = "SELECT * FROM NOTES WHERE noteId = #{noteId}";
        public static final String SELECT_CREDENTIAL_BY_CREDENTIAL_ID = "SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}";
    }
    
    /**
     * The type Insert sql.
     */
    public class InsertSQL {
        /**
         * The constant INSERT_USER.
         */
        public static final String INSERT_USER = "INSERT INTO USERS (username, salt, password, firstname, lastname) " +
                "VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})";
        
        /**
         * The constant INSERT_FILE.
         */
        public static final String INSERT_FILE = "INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
                "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})";
        /**
         * The constant INSERT_CREDENTIAL.
         */
        public static final String INSERT_CREDENTIAL = "INSERT INTO CREDENTIALS (url, username, key, password, userid) " +
                "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})";
        
        /**
         * The constant INSERT_NOTE.
         */
        public static final String INSERT_NOTE = "INSERT INTO NOTES (notetitle, notedescription, userid) " +
                "VALUES(#{noteTitle}, #{noteDescription}, #{userId})";
    }
    
    /**
     * The type Delete sql.
     */
    public class DeleteSQL {
        /**
         * The constant DELETE_FILE.
         */
        public static final String DELETE_FILE = "DELETE FROM FILES WHERE fileId = #{fileId}";
        /**
         * The constant DELETE_CREDENTIAL.
         */
        public static final String DELETE_CREDENTIAL = "DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}";
        /**
         * The constant DELETE_NOTE.
         */
        public static final String DELETE_NOTE = "DELETE FROM NOTES WHERE noteId = #{noteId}";
    }
    
    
    /**
     * The type Update sql.
     */
    public class UpdateSQL {
        /**
         * The constant UPDATE_CREDENTIAL.
         */
        public static final String UPDATE_CREDENTIAL = "UPDATE CREDENTIALS SET url = #{url}, key = #{key}, password = " +
                "#{password}, username = #{newUserName} WHERE credentialId = #{credentialId}";
        
        /**
         * The constant UPDATE_NOTE.
         */
        public static final String UPDATE_NOTE = "UPDATE NOTES SET notetitle = #{noteTitle}, " +
                "notedescription = #{noteDescription} WHERE noteId = #{noteId}";
    }
}
