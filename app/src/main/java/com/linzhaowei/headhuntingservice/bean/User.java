package com.linzhaowei.headhuntingservice.bean;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;

    private String username;

    private String userpsd;

    private Integer status;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getUserpsd() {
        return userpsd;
    }

    public void setUserpsd(String userpsd) {
        this.userpsd = userpsd == null ? null : userpsd.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}