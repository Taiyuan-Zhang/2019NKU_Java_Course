package com.springmvc.entity;

import java.util.Date;

public class Userinfo {
    private Integer euid;

    private String id;

    private String number;

    private Double money;

    private String pwd;

    private Integer talktime;

    private Integer send;

    private Integer flow;

    private Double consume;

    private String Package;

    private Date createtime;

    public Integer getEuid() {
        return euid;
    }

    public void setEuid(Integer euid) {
        this.euid = euid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getTalktime() {
        return talktime;
    }

    public void setTalktime(Integer talktime) {
        this.talktime = talktime;
    }

    public Integer getSend() {
        return send;
    }

    public void setSend(Integer send) {
        this.send = send;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    public Double getConsume() {
        return consume;
    }

    public void setConsume(Double consume) {
        this.consume = consume;
    }

    public String getPackage() {
        return Package;
    }

    public void setPackage(String Package) {
        this.Package = Package;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}