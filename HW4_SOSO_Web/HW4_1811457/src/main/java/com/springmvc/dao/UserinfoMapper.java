package com.springmvc.dao;

import com.springmvc.entity.Userinfo;

import java.util.ArrayList;

public interface UserinfoMapper {
    int deleteByPrimaryKey(Integer euid);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer euid);

    ArrayList<Userinfo> selectSelective(Userinfo record);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
}