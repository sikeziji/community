package com.wangj.community.mapper;

import com.wangj.community.module.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modified,avatar_url,bio) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl},#{bio})")
    void insert(User user);

    @Select("select * from USER where token = #{token}")
    User findByToken(@Param("token")  String token);

    @Select("select * from USER where id = #{id}")
    User findByID(@Param("id") Integer id);

    @Select("select * from USER where account_id = #{accountId}")
    User findByAccountId(@Param(value = "accountId") String accountId);

    @Update("update USER set name = #{name} ,token = #{token} , gmt_modified = #{gmtModified},avatar_url = #{avatarUrl} where id = #{id}")
    void update(User user);
}
