package com.springmvc.controller;

import com.springmvc.entity.Userinfo;
import com.springmvc.service.UserinfoService;
import com.springmvc.util.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class userController {
    @Autowired
    private UserinfoService userService;

    @RequestMapping(value="/user/login", method = RequestMethod.GET)
    public String login() {
        System.out.println("good1");
        return "login";

    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public String loginValidate(HttpSession session, Model model, @ModelAttribute Userinfo user) {
        List<Userinfo> list = new ArrayList<Userinfo>();
        Userinfo record  = new Userinfo();
        record.setNumber(user.getNumber());
        list = userService.selectSelective(record);
        System.out.println(list.size());
        if (list.size() == 0) {
            model.addAttribute("status", 1);
        } else {
            record.setPwd(Encryption.MD5(user.getPwd()));
            list = userService.selectSelective(record);
            if (list.size() == 0) {
                model.addAttribute("status", 2);
            }
            record = list.get(0);
            session.setAttribute("userinfo", record);
            model.addAttribute("status", 0);
        }
        System.out.println("good2");
        return "login";
    }

    @RequestMapping(value = "/user/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        //session.removeAttribute("user");
        return "login";
    }

    @RequestMapping(value="/user/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, HttpSession session) {
        Userinfo user = (Userinfo) session.getAttribute("userinfo");
        if(user != null){
            model.addAttribute("user", user);
        }

        return "userInfo";
    }

    @RequestMapping(value="/user/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value="/user/register", method = RequestMethod.POST)
    public String addUser(@ModelAttribute Userinfo user, Model model) {
        Userinfo record = new Userinfo();
        record.setNumber(user.getNumber());
        List<Userinfo> list = userService.selectSelective(record);
        if (list.size() == 0) {
            user.setCreatetime(new Date());
            user.setPwd(Encryption.MD5(user.getPwd()));
            if (userService.insert(user) == 1) {
                model.addAttribute("status", 0);
            } else {
                model.addAttribute("status", 1);
            }
        } else {
            model.addAttribute("status", 2);
        }
        System.out.println("good4");
        System.out.println(userService.insert(user));
        return "register";
    }
}
