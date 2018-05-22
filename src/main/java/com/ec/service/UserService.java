package com.ec.service;

import com.ec.controller.resp.RespUser;
import com.ec.entity.Instructor;
import com.ec.entity.Student;

import java.util.List;

public interface UserService {

    //根据用户名密码找学生
    List<Student> findStudent(String username, String password);
    //根据用户名密码找老师
    List<Instructor> findInstructor(String username, String password);
    //学生注册
    int registerStudent(String username, String password, String email);
    //老师注册
    int registerInstructor(String username, String password, String email);
    //修改学生信息
    int repairStudent(String username,String password,String email);
    //修改老师信息
    int repairInstructor(String username, String password, String email);
    //登陆功能
    RespUser findUser(String username, String password, String isStudent);
    //注册功能
    RespUser registerUser(String username, String password, String isStudent, String email);
    //修改功能
    RespUser repairUser(String username, String password, String isStudent, String email);
    //查询所有项目信息
    RespUser findAllUserInfo(int studentId);

}
