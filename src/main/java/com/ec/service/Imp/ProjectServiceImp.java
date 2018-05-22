package com.ec.service.Imp;

import com.ec.controller.resp.RespBody;
import com.ec.entity.*;
import com.ec.mapper.InstructorMapper;
import com.ec.mapper.ParticipateMapper;
import com.ec.mapper.StudentMapper;
import com.ec.service.AccessService;
import com.ec.service.DupNameService;
import com.ec.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.ec.mapper.ProjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service

public class ProjectServiceImp implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private InstructorMapper instructorMapper;
    @Autowired
    private ParticipateMapper participateMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    AccessService accessService;
    @Autowired
    DupNameService dupNameService;

    public RespBody createProject(String studentName, String instructorName, String pTitle, String pDescription) {

        if(pTitle.trim()==null||pTitle.trim().equals("")){//用户名为空
            return RespBody.build(500,"PTITLE CAN'T BE EMPTY",new ArrayList());
        }else {
            ProjectExample projectExample = new ProjectExample();
            ProjectExample.Criteria criteria = projectExample.createCriteria();//构造自定义查询条件
            criteria.andPTitleEqualTo(pTitle);
            List<Project> projects = projectMapper.selectByExample(projectExample);
            if (!projects.isEmpty()) {
                return RespBody.build(500,"THE PROJECT ALREADY EXISTS",new ArrayList());
            } else {
                InstructorExample instructorExample=new InstructorExample();
                InstructorExample.Criteria criteria1=instructorExample.createCriteria();
                criteria1.andNameEqualTo(instructorName);
                List<Instructor> instructorList=instructorMapper.selectByExample(instructorExample);

                if (instructorList.isEmpty()){
                    return RespBody.build(500,"THE INSTRUCTOR DOES NOT EXISTS",new ArrayList());
                }

                StudentExample studentExample=new StudentExample();
                StudentExample.Criteria criteria2=studentExample.createCriteria();
                criteria2.andNameEqualTo(studentName);
                List<Student> studentList=studentMapper.selectByExample(studentExample);

                if (studentList.isEmpty()){
                    return RespBody.build(500,"THE STUDENT DOES NOT EXISTS",new ArrayList());
                }
                Project project = new Project();
                project.setpTitle(pTitle);
                project.setpDescription(pDescription);
                project.setInstructorId(instructorList.get(0).getInstructorId());
                project.setpBeginTime(new Date());


                int insertProject = projectMapper.insertSelective(project);
                criteria.andPTitleEqualTo(pTitle);
                projects = projectMapper.selectByExample(projectExample);

                if(insertProject!=0){
                    Participate participate=new Participate();
                    participate.setProjectId(projects.get(0).getProjectId());
                    participate.setStudentId(studentList.get(0).getStudentId());
                    participate.setRole(1);
                    participate.setParticipateTime(new Date());
                    int insertParticipate=participateMapper.insertSelective(participate);
                    if(insertParticipate==0) {
                        return RespBody.build(500,"INTERNAL ERROR",new ArrayList());
                    } else {
                        return RespBody.build(projectMapper.selectByExample(projectExample));
                    }
                }
                return RespBody.build(500,"INTERNAL ERROR",new ArrayList());
            }
        }
    }

    public RespBody displayProject(int studentId){

        List<Project> projectList = new ArrayList<Project>();
        ParticipateExample participateExample=new ParticipateExample();
        ParticipateExample.Criteria criteria=participateExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        List<Participate> participateList=participateMapper.selectByExample(participateExample);

        if(participateList.isEmpty()){
            return RespBody.build(500,"NO PROJECT",new ArrayList());
        }

        List<Integer>projectIdList=new ArrayList<Integer>();
        for(int i=0;i<participateList.size();i++){
            projectIdList.add(participateList.get(i).getProjectId());
        }

        ProjectExample projectExample = new ProjectExample();
        ProjectExample.Criteria criteria1 = projectExample.createCriteria();
        criteria1.andProjectIdIn(projectIdList);
        projectList = projectMapper.selectByExample(projectExample);

        return RespBody.build(projectList);

    }

    public RespBody repairProject(String pTitle, String pDescription) {
        if(pTitle.trim()==null||pTitle.trim().equals("")){//项目名为空
            return RespBody.build(500,"PTITLE CAN'T BE EMPTY",new ArrayList());
        }else {
            ProjectExample projectExample = new ProjectExample();
            ProjectExample.Criteria criteria = projectExample.createCriteria();//构造自定义查询条件
            criteria.andPTitleEqualTo(pTitle);
            List<Project> projects = projectMapper.selectByExample(projectExample);
            if (projects.isEmpty()) {
                return RespBody.build(500,"THE PROJECT DOES NOT EXISTS",new ArrayList());
            } else {
                Project project=projects.get(0);
                project.setpTitle(pTitle);
                project.setpDescription(pDescription);
                int repairProject = projectMapper.updateByPrimaryKey(project);
                if(repairProject==0) {
                    return RespBody.build(500,"INTERNAL ERROR",new ArrayList());
                } else {
                    return RespBody.build(projects);
                }
            }
        }
    }

    public int deleteProjectById (Integer projectId, Integer studentId){   //根据ProjectId删除project的实现
        if(projectId<=0||studentId<=0){//若ProjectId或studentId为非正数
            return -1;
        }
        else{

            ParticipateExample participateExample=new ParticipateExample();
            ParticipateExample.Criteria criteria=participateExample.createCriteria();
            criteria.andStudentIdEqualTo(studentId);
            criteria.andProjectIdEqualTo(projectId);
            criteria.andRoleEqualTo(1);
            List<Participate> participateList=participateMapper.selectByExample(participateExample);
            if(participateList.isEmpty()){
                return 0;                    //该学生没有删除此项目的权限（不是组长或者根本没参加此项目）
            }
            projectMapper.deleteByPrimaryKey(projectId);
            return 1;

        }
    }

    public List<Student> getAllMemberById(Integer projectId) {
        ParticipateExample participateExample = new ParticipateExample();
        ParticipateExample.Criteria criteria= participateExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        List<Participate> participates = participateMapper.selectByExample(participateExample);

        List<Integer> studentIdList = new ArrayList<Integer>();
        Iterator it = participates.iterator();
        while(it.hasNext()){
            studentIdList.add(((Participate) it.next()).getStudentId());
        }

        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria c = studentExample.createCriteria();
        c.andStudentIdIn(studentIdList);
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        return studentList;
    }

    public RespBody saveMember(Integer studentId, Integer projectId, String memberName) {

        //组长权限检测
        if(!accessService.isAccessToPro(projectId,studentId)){
            return RespBody.build(500,"没有操作权限",new ArrayList());
        }

        //加入的成员是否在系统中
        if(!dupNameService.isDupUserNameInSys("true",memberName)){
            return RespBody.build(500,"该成员还没有注册",new ArrayList());
        }

        //是否重复加入
        if(dupNameService.isDupUserNameInPro(projectId,memberName)){
            return RespBody.build(500,"重复添加",new ArrayList());
        }

        //添加成员
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria c = studentExample.createCriteria();
        c.andNameEqualTo(memberName);
        List<Student> studentList = studentMapper.selectByExample(studentExample);
        Participate p = new Participate();
        p.setProjectId(projectId);
        p.setStudentId(studentList.get(0).getStudentId());
        p.setParticipateTime(new Date());
        p.setRole(0);
        int i = participateMapper.insertSelective(p);

        return RespBody.build(new ArrayList());

    }
}
