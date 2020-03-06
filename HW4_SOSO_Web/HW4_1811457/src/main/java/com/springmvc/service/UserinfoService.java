package com.springmvc.service;

import com.springmvc.entity.Userinfo;

import java.util.ArrayList;

public interface UserinfoService {
    int insert(Userinfo record);

    ArrayList<Userinfo> selectSelective(Userinfo record);
}
