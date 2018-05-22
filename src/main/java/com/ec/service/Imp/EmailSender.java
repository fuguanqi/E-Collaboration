package com.ec.service.Imp;

import com.ec.entity.Participate;
import com.ec.entity.Student;


import java.util.Iterator;
import java.util.List;

public class EmailSender implements Runnable{

    int projectId;
    String subject;
    String content;

    public EmailSender( int projectId, String subject, String content) {

        this.projectId = projectId;
        this.subject = subject;
        this.content = content;

    }

    public void run() {
        List<Student> participateList =
                new ProjectServiceImp().getAllMemberById(this.projectId);
        Iterator iterator = participateList.iterator();
        while(iterator.hasNext()){
            Student s = (Student) iterator.next();
            Integer id = s.getStudentId();
            new MailServiceImp().sendEmail(id ,this.projectId,this.subject,this.content);
        }
        System.out.println(projectId+":"+subject+":"+content);

    }
}
