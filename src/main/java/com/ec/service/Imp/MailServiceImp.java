package com.ec.service.Imp;

import com.ec.entity.*;
import com.ec.mapper.*;
import com.ec.controller.resp.RespBody;
import com.ec.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MailServiceImp implements MailService {


    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private ProjectMapper projctMapper;
    @Autowired
    private MailMapper mailMapper;
    @Autowired
    private JavaMailSenderImpl javaMailSenderImpl;

    public RespBody sendEmail( int toId ,int projectId, String subject, String content) {

        //邮件主题和内容不能为空
        if(subject.trim()==null||subject.trim().equals("")||content==null||content.trim().equals("")){
            return RespBody.build(500,"SUBJECT CAN'T BE EMPTY",new ArrayList());
        }

        //项目不能为空
        if(projctMapper.selectByPrimaryKey(projectId)==null){
            return RespBody.build(500,"FOREIGN KEY CONSTRAINTS NOT SATISFIED",new ArrayList());
        }

        //构造邮箱对象
        Mail mail=new Mail();
        mail.setToId(toId);
        mail.setSubject(subject);
        mail.setProjectId(projectId);
        mail.setContent(content);
        mail.setSendTime(new Date());

        //发送邮件
        int rst=createMimeMessage(mail);

        if (rst==1){
            //发送成功将记录保存在数据库中
            mailMapper.insertSelective(mail);
            return RespBody.build(new ArrayList());
        }
        else if (rst==0){
            return RespBody.build(500,"EMAIL NOT SENT-MESSAGING EXCEPTIONG",new ArrayList());
        }else {
            return RespBody.build(500,"UnsupportedEncodingException",new ArrayList());
        }
    }

    int createMimeMessage(Mail mail) {
        int result;

        //创建邮件对象
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            String html =   "<html><head>"+
                            "</head><body>"+
                            "<h1>【E_Collaboration】</h1>"+
                            "<span style='color:red;font-size:25px;'>项目名:"+projctMapper.selectByPrimaryKey(mail.getProjectId()).getpTitle()+"</span>"+
                            "<div style='font-size:20px;'>通知内容："+mail.getContent()+"</div>"+
                            "</body></html>";
            //设置发件人
            messageHelper.setFrom("e_collaboration@163.com", "E-Collab");

            //设置收件人
            messageHelper.setTo(studentMapper.selectByPrimaryKey(mail.getToId()).getEmailAdd());

            //设置邮件主题
            messageHelper.setSubject(mail.getSubject());

            //设置邮件内容
            messageHelper.setText(html,true);

            //发送邮件
            javaMailSenderImpl.send(mimeMessage);

            //发送成功返回1
            result =1;
        } catch (MessagingException e) {
            result = 0;
            e.printStackTrace();
        }catch (UnsupportedEncodingException e){
            result =-1;
            e.printStackTrace();
        }
        return result;
    }
}
