package com.ec.service.Imp;

import com.ec.controller.converter.CustomDateConverter;
import com.ec.controller.resp.RespBody;
import com.ec.entity.*;
import com.ec.mapper.ParticipateMapper;
import com.ec.mapper.PhaseMapper;
import com.ec.mapper.TaskMapper;
import com.ec.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class TaskServiceImp implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private ParticipateMapper participateMapper;
    @Autowired
    private PhaseMapper phaseMapper;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private MailService mailService;
    @Autowired
    AccessService accessService;
    @Autowired
    DupNameService dupNameService;


    public RespBody createTask(Integer userid, Integer studentId, final Integer projectId, Integer phaseId, final String tTitle, final String tDescription, String deadline, Integer workload) {

        //查看是否有组长权限
        if(!accessService.isAccessToPro(projectId,userid)){
            return RespBody.build(500,"没有组长权限",new ArrayList());
        }

        //阶段是否已经建立
        if(phaseMapper.selectByPrimaryKey(phaseId)==null){
            return RespBody.build(500,"阶段不存在",new ArrayList());
        }

        //查看任务是否重名
        if(dupNameService.isDupTaskNameInPh(projectId,phaseId,tTitle)){
            return RespBody.build(500,"任务重名了",new ArrayList());
        }

        //构造新任务并插入数据库
        Task task=new Task();
        task.setStudentId(studentId);
        task.setProjectId(projectId);
        task.setPhaseId(phaseId);
        task.settTitle(tTitle);
        task.settDescription(tDescription);
        task.setWorkload(workload);
        task.setStatus(0);
        CustomDateConverter customDateConverter=new CustomDateConverter();//日期转换器
        task.setDeadline(customDateConverter.convert(deadline));
        task.settBeginTime(new Date());
        int t=taskMapper.insertSelective(task);
        if (t==0){
            return RespBody.build(500,"INTERNAL ERROR",new ArrayList());
        }

        //通知项目的所有成员
        new Thread(new Runnable() {
            public void run() {
                List<Student> participateList = projectService.getAllMemberById(projectId);
                Iterator iterator = participateList.iterator();
                while(iterator.hasNext()){
                    Student s = (Student) iterator.next();
                    Integer id = s.getStudentId();
                    mailService.sendEmail(id,projectId,"新建任务",
                                          "任务名："+tTitle+",任务说明："+tDescription);
                }
            }
        }).start();

        //EmailSender emailSender = new EmailSender(projectId, "新建任务",
        //        "任务名：" + tTitle + ",任务说明：" + tDescription);
        //new Thread(emailSender).start();

        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria criteria = taskExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        criteria.andPhaseIdEqualTo(phaseId);
        criteria.andTTitleEqualTo(tTitle);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        return RespBody.build(taskList);
    }

    public RespBody display(Integer studentId) {

        TaskExample taskExample=new TaskExample();
        TaskExample.Criteria criteria=taskExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        List<Task> taskList = taskMapper.selectByExample(taskExample);

        return RespBody.build(taskList);
    }

    public RespBody display(Integer studentId, Integer projectId, Integer phaseId) {
        TaskExample taskExample=new TaskExample();
        TaskExample.Criteria criteria=taskExample.createCriteria();
        //criteria.andStudentIdEqualTo(studentId);
        criteria.andProjectIdEqualTo(projectId);
        criteria.andPhaseIdEqualTo(phaseId);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        return RespBody.build(taskList);
    }


    public List<Task> display(Integer studentId, Integer projectId) {
        TaskExample taskExample=new TaskExample();
        TaskExample.Criteria criteria=taskExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andProjectIdEqualTo(projectId);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        return taskList;
    }

    public RespBody updateTaskCommit(int userId, int taskId) {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria c = taskExample.createCriteria();
        c.andTaskIdEqualTo(taskId).andStudentIdEqualTo(userId);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        if(taskList==null||taskList.isEmpty()){
            RespBody.build(500,"提交失败",new ArrayList());
        }
        Task t = taskList.get(0);
        t.setStatus(1);
        int i = taskMapper.updateByPrimaryKey(t);
        if(i==0){
            RespBody.build(500,"提交失败",new ArrayList());
        }
        return  RespBody.build(new ArrayList());
    }


}
