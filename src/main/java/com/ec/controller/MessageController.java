package com.ec.controller;

import com.ec.controller.resp.RespJson;
import com.ec.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@CrossOrigin
@RequestMapping("message")
public class MessageController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/send")
    @ResponseBody
    public  String sendMessage(HttpServletRequest request, String callback)throws Exception{
        String projectId=request.getParameter("projectId");
        int i = Integer.parseInt(projectId);

        String senderName=request.getParameter("senderName");
        String content=request.getParameter("content");

        int t = messageService.sendMessage(i,senderName,content);
        return RespJson.getString(callback, t);

    }
}
