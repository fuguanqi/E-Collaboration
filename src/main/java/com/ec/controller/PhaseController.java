package com.ec.controller;


import com.ec.controller.resp.RespBody;
import com.ec.controller.resp.RespJson;
import com.ec.service.PhaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("phase")
public class PhaseController {

    @Autowired
    private PhaseService phaseService;

    /**
     * 项目下新建阶段
     */
    @RequestMapping("/createPhase")
    public void createPhase (HttpServletRequest request, HttpServletResponse response, String callback)throws Exception{

        //获取参数
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        String phTitle = request.getParameter("phTitle");
        String phDescription = request.getParameter("phDescription");

        //向service层发送新建请求
        RespBody respPhase= phaseService.createPhase(studentId,projectId,phTitle,phDescription);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback,respPhase));
    }

    /**
     * 查看项目下所有的阶段
     */
    @RequestMapping("/displayPhase")
    public void displayPhase(HttpServletRequest request,HttpServletResponse response,String callback)throws Exception{

        //获取参数
        //Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));

        //向Service层发送请求
        RespBody respbody = phaseService.findPhase(projectId);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback,respbody));
    }

}
