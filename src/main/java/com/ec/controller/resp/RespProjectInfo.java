package com.ec.controller.resp;

import com.ec.entity.Project;
import com.ec.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class RespProjectInfo {
    private Project project;


    private List<Student> memberList;

    private List<Object> projectfileList = new ArrayList<Object>();

    private List<RespPhaseInfo> phaseInfos = new ArrayList<RespPhaseInfo>();

    public List<Student> getMemberList() { return memberList;}

    public void setMemberList(List<Student> memberList) { this.memberList = memberList;}

    public Object getProject() {
        return project;
    }

    public void setProject(Project project) {this.project = project;}

    public List<RespPhaseInfo> getPhaseInfos() {
        return phaseInfos;
    }

    public void setPhaseInfos(List<RespPhaseInfo> phaseInfos) {
        this.phaseInfos = phaseInfos;
    }

    public List<Object> getProjectfileList() {
        return projectfileList;
    }

    public void setProjectfileList(List<Object> projectfileList) {
        this.projectfileList = projectfileList;
    }
}
