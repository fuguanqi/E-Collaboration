package com.ec.service.Imp;

import com.ec.entity.*;
import com.ec.mapper.*;
import com.ec.service.DupNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DupNameServiceImp implements DupNameService {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    InstructorMapper instructorMapper;
    @Autowired
    ParticipateMapper participateMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    PhaseMapper phaseMapper;
    @Autowired
    TaskMapper taskMapper;


    public Boolean isDupUserNameInSys(String isStudent, String userName) {
        if(isStudent.equalsIgnoreCase("true")){//学生
            StudentExample studentExample = new StudentExample();
            StudentExample.Criteria criteria = studentExample.createCriteria();//构造自定义查询条件
            criteria.andNameEqualTo(userName);
            List<Student> userList = studentMapper.selectByExample(studentExample);
            if(userList==null||userList.isEmpty()){
                return false;
            }
            return true;
        }else if (isStudent.equalsIgnoreCase("false")){//老师
            InstructorExample instructorExample = new InstructorExample();
            InstructorExample.Criteria criteria = instructorExample.createCriteria();
            criteria.andNameEqualTo(userName);
            List<Instructor> userList = instructorMapper.selectByExample(instructorExample);
            if(userList==null||userList.isEmpty()){
                return false;
            }
            return false;
        }else{
            return null;
        }
    }

    public Boolean isDupUserNameInPro(Integer projectId, String userName) {

        //通过学生的用户名查学生表获取学生的id
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria c1= studentExample.createCriteria();
        c1.andNameEqualTo(userName);
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        Integer studentId = studentList.get(0).getStudentId();

        //在参与表中查询是否存在此记录
        ParticipateExample participateExample = new ParticipateExample();
        ParticipateExample.Criteria c2 = participateExample.createCriteria();
        c2.andProjectIdEqualTo(projectId).andStudentIdEqualTo(studentId);
        List<Participate> participateList = participateMapper.selectByExample(participateExample);
        if(participateList==null||participateList.isEmpty()){
            return false;
        }
        return true;
    }

    public Boolean isDupProjectName(String projectName) {
        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria c = projectExample.createCriteria();
        c.andPTitleEqualTo(projectName);
        List<Project> projectList = projectMapper.selectByExample(projectExample);
        if(projectList==null||projectList.isEmpty()){
            return  false;
        }
        return true;
    }

    public Boolean isDupPhaseNameInPro(Integer projectId, String phaseName) {
        PhaseExample phaseExample = new PhaseExample();
        PhaseExample.Criteria c = phaseExample.createCriteria();
        c.andProjectIdEqualTo(projectId).andPhTitleEqualTo(phaseName);
        List<Phase> phaseList = phaseMapper.selectByExample(phaseExample);
        if(phaseList==null||phaseList.isEmpty()){
            return false;
        }
        return true;
    }

    public Boolean isDupTaskNameInPh(Integer projectId, Integer phaseId, String taskName) {
        TaskExample taskExample = new TaskExample();
        TaskExample.Criteria c = taskExample.createCriteria();
        c.andProjectIdEqualTo(projectId).andPhaseIdEqualTo(phaseId).andTTitleEqualTo(taskName);
        List<Task> taskList = taskMapper.selectByExample(taskExample);
        if(taskList==null||taskList.isEmpty()){
            return false;
        }
        return true;
    }

}
