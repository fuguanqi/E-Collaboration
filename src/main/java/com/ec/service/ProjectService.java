package com.ec.service;
import com.ec.controller.resp.RespBody;
import com.ec.entity.Student;

import java.util.List;

public interface ProjectService {

    //新增项目
    RespBody createProject(String studentName, String instructorName, String pTitle, String pDescription);

    //查看项目
    RespBody displayProject(int studentId);

    //修改项目
    RespBody repairProject(String pTitle, String pDescription);

    //删除项目
    int deleteProjectById(Integer projectId, Integer studentId);

    //查询项目所有成员
    List<Student> getAllMemberById(Integer projectId);

    //为项目添加成员
    RespBody saveMember(Integer studentId, Integer projectId, String memberName);

}
