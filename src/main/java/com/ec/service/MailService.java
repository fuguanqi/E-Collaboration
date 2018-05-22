package com.ec.service;

import com.ec.controller.resp.RespBody;

public interface MailService {
    //发送邮件
    RespBody sendEmail(int toId,int projectId,String subject, String content);
}
