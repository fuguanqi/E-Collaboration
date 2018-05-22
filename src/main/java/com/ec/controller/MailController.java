package com.ec.controller;

import com.ec.controller.resp.RespBody;
import com.ec.controller.resp.RespJson;
import com.ec.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@CrossOrigin
@RequestMapping("mail")
public class MailController {

    @Autowired
    private MailService mailService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 发送邮件
     */
    @RequestMapping("/sendEmail")
    public void createProject(HttpServletRequest request, HttpServletResponse response , String callback)throws Exception {

        //获取参数
        String toId=request.getParameter("toId");
        String projectId = request.getParameter("projectId");
        String subject = request.getParameter("subject");
        String content = request.getParameter("content");


//        taskExecutor.execute(
//                new Runnable(){
//                    @Override
//                    public void run() {//执行的代码
//                        mailService.sendEmail(Integer.parseInt(toId),Integer.parseInt(projectId), subject, content);
//                    }
//                });

        //向service层发送创建请求
        RespBody respmail = mailService.sendEmail(Integer.parseInt(toId),Integer.parseInt(projectId), subject, content);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback, respmail));
    }
}
