package com.wangj.community.mapper;

import com.wangj.community.module.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into USER(name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from USER where token = #{token}")
    User findByToken(@Param("token")  String token);

    @Select("select * from USER where id = #{id}")
    User findByID(@Param("id") Integer id);
}
