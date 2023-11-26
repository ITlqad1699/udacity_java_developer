package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.constant.CommonConstant;
import com.udacity.jwdnd.course1.cloudstorage.constant.SQLConstant;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


/**
 * The interface User mapper.
 */
@Mapper
public interface UserMapper {
    
    /**
     * Gets user by user name.
     *
     * @param username the username
     * @return the user by user name
     */
    @Select(SQLConstant.SelectSQL.SELECT_USER_BY_USER_NAME)
    User getUserByUserName(String username);
    
    /**
     * Gets user by user id.
     *
     * @param userId the user id
     * @return the user by user id
     */
    @Select(SQLConstant.SelectSQL.SELECT_USER_BY_USER_ID)
    User getUserByUserId(Integer userId);
    
    /**
     * Insert int.
     *
     * @param user the user
     * @return the int
     */
    @Insert(SQLConstant.InsertSQL.INSERT_USER)
    @Options(useGeneratedKeys = true, keyProperty = CommonConstant.USER_ID)
    int insert(User user);
    
}
