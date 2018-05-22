package com.ec.controller;

import com.ec.controller.resp.RespJson;
import com.ec.controller.resp.RespBody;
import com.ec.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 创建项目
     */
    @RequestMapping("/createProject")
    public void createProject(HttpServletRequest request, HttpServletResponse response ,String callback)throws Exception {
 
        //获取参数
        String studentName=request.getParameter("studentName");
        String instructorName = request.getParameter("instructorName");
        String pTitle = request.getParameter("pTitle");
        String pDescription = request.getParameter("pDescription");

        //向service层发送创建请求
        RespBody respProject = projectService.createProject(studentName,instructorName, pTitle, pDescription);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback, respProject));
    }

    /**
     * 查询学生参加的所有项目
     */
    @RequestMapping("/displayProject")
    public void display(HttpServletRequest request, String callback, HttpServletResponse response)throws Exception {

        //获取参数
        String studentId=request.getParameter("studentId");

        //向service层发送查看请求
        int i = Integer.parseInt(studentId);
        RespBody respProject = projectService.displayProject(i);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback, respProject));
    }

    /**
     * 修改项目描述
     */
    @RequestMapping("/repairProject")
    public void repairProject(HttpServletRequest request, String callback, HttpServletResponse response)throws Exception{

        //获取参数
        String pTitle = request.getParameter("pTitle");
        String pDescription = request.getParameter("pDescription");

        //向service层发送修改请求
        RespBody respProject = projectService.repairProject(pTitle, pDescription);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback, respProject));

    }


    /**
     * 为项目添加成员
     */
    @RequestMapping("/addMember")
    public void addMember(HttpServletRequest request,HttpServletResponse response,String callback)throws Exception{

        //获取参数
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer projectId  = Integer.parseInt(request.getParameter("projectId"));
        String memberName = request.getParameter("memberName");

        //向Service层发送请求
        RespBody respBody = projectService.saveMember(studentId, projectId, memberName);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback, respBody));

    }


}
