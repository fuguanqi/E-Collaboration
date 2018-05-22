package com.ec.controller.resp;

import com.ec.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class RespUserInfo {
    private Object user;
    private List<RespProjectInfo> projects =new ArrayList<RespProjectInfo>();


    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public List<RespProjectInfo> getProjects() {
        return projects;
    }

    public void setProjects(List<RespProjectInfo> projects) {
        this.projects = projects;
    }
}
