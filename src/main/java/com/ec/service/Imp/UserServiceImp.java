package com.ec.service.Imp;

import com.ec.controller.resp.*;
import com.ec.entity.*;
import com.ec.mapper.InstructorMapper;
import com.ec.mapper.StudentMapper;
import com.ec.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private InstructorMapper instructorMapper;
    @Autowired
    ProjectService projectService;
    @Autowired
    PhaseService phaseService;
    @Autowired
    TaskService taskService;
    @Autowired
    FileService fileService;
    @Autowired
    DupNameService dupNameService;

    public List<Student> findStudent(String username, String password) {

        System.out.println("我要找学生");
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();//构造自定义查询条件

        criteria.andNameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<Student> studentList = studentMapper.selectByExample(studentExample);

        return studentList;
    }

    public List<Instructor> findInstructor(String username, String password) {
        System.out.println("我要找老师");
        InstructorExample instructorExample = new InstructorExample();
        InstructorExample.Criteria criteria = instructorExample.createCriteria();
        criteria.andNameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<Instructor> instructorList = instructorMapper.selectByExample(instructorExample);
        return instructorList;
    }

    public int registerStudent(String username, String password, String email) {

        //用户名非空检查
        if(username.trim()==null||username.trim().equals("")){
            return 0;
        }

        //密码非空检查
        if(password.trim()==null||password.trim().equals("")){
            return 0;
        }

        //重名检查
        if(dupNameService.isDupUserNameInSys("true",username)){
            return 0;
        }

        //向数据库插入注册学生记录
        Student student = new Student();
        student.setName(username);
        student.setPassword(password);
        student.setEmailAdd(email);
        int insertUser = studentMapper.insertSelective(student);
        return insertUser;
    }

    public int registerInstructor(String username, String password, String email) {

        //用户名非空检查
        if(username.trim()==null||username.trim().equals("")){//用户名为空
            return 0;
        }

        //密码非空检查
        if(password.trim()==null||password.trim().equals("")){//密码为空
            return 0;
        }

        //重名检查
        if (dupNameService.isDupUserNameInSys("false",username)) {
            return 0;
        }

        //向数据库插入注册老师记录
        Instructor instructor = new Instructor();
        instructor.setName(username);
        instructor.setPassword(password);
        instructor.setEmailAdd(email);
        int insertUser = instructorMapper.insertSelective(instructor);
        return insertUser;

    }

    public int repairStudent(String username, String password, String email) {
        if (username.trim() == null || username.trim().equals("")) {//用户名为空
            return 0;
        }
        else {
            StudentExample studentExample = new StudentExample();
            StudentExample.Criteria criteria = studentExample.createCriteria();//构造自定义查询条件
            criteria.andNameEqualTo(username);
            List<Student> userList = studentMapper.selectByExample(studentExample);
            if (userList.isEmpty()) {
                return 0;
            } else {

                Student student=userList.get(0);
                student.setEmailAdd(email);
                student.setPassword(password);
                //int repairUser = studentMapper.updateByExampleSelective(student,studentExample);
                int repairUser = studentMapper.updateByPrimaryKey(student);
                return repairUser;
            }
        }
    }

    public int repairInstructor(String username, String password, String email) {
        if (username.trim() == null || username.trim().equals("")) {//用户名为空
            return 0;
        }
        else {
            InstructorExample instructorExample = new InstructorExample();
            InstructorExample.Criteria criteria = instructorExample.createCriteria();//构造自定义查询条件
            criteria.andNameEqualTo(username);
            List<Instructor> userList = instructorMapper.selectByExample(instructorExample);
            if (userList.isEmpty()) {
                return 0;
            } else {

                Instructor instructor=userList.get(0);
                instructor.setEmailAdd(email);
                instructor.setPassword(password);
                //int repairUser = studentMapper.updateByExampleSelective(student,studentExample);
                int repairUser = instructorMapper.updateByPrimaryKey(instructor);
                return repairUser;
            }
        }
    }

    public RespUser findUser(String username, String password, String isStudent) {
        if(!dupNameService.isDupUserNameInSys(isStudent,username)){
            return RespUser.build(500,"NO FIND THIS USER");
        }
        if(isStudent.equals("true")){//学生登陆
            List<Student> studentList = findStudent(username, password);
            if(studentList.isEmpty()) {
                return RespUser.build(500,"NO FIND THIS USER");
            }else {
                return RespUser.build(studentList.get(0));
            }
        }else {//老师登陆
            List<Instructor> instructorList = findInstructor(username, password);
            if(instructorList.isEmpty()) {
                return RespUser.build(500,"NO FIND THIS USER");
            }else {
                return RespUser.build(instructorList.get(0));
            }
        }
    }

    public RespUser registerUser(String username, String password, String isStudent, String email) {
        if(dupNameService.isDupUserNameInSys(isStudent,username)){
            return RespUser.build(500,"THE DUPLICATION OF NAME");
        }
        int registerUser;
        if(isStudent.equals("true")){//学生
            registerUser = registerStudent(username, password,email);
        }else{//老师
            registerUser = registerInstructor(username, password,email);
        }

        if(registerUser!=0) {
            return RespUser.build(null);
        } else {
            return RespUser.build(500,"FALSE");
        }
    }

    public RespUser repairUser(String username, String password, String isStudent, String email) {
        int repairUser;

        if(isStudent.equals("true")){//学生
            repairUser = repairStudent(username, password,email);
        }else{//老师
            repairUser = repairInstructor(username, password,email);
        }

        if(repairUser!=0) {
            return RespUser.build(null);
        } else {
            return RespUser.build(500,"FALSE");
        }
    }

    public RespUser findAllUserInfo(int studentId) {
        //用学生id获得项目的列表projectList
        RespBody respBody =projectService.displayProject(studentId);
        List<Object> projectList = respBody.getData();

        RespUserInfo respUserInfo = new RespUserInfo();

        //遍历项目列表projectList获取每个项目的阶段列表phaseList
        Iterator iterator = projectList.iterator();
        while(iterator.hasNext()){

            //用项目id获得每个项目下的阶段列表
            Project p=(Project)iterator.next();
            RespBody phase = phaseService.findPhase(p.getProjectId());
            List<Object> phaseList = phase.getData();


            RespProjectInfo respProjectInfo = new RespProjectInfo();
            respProjectInfo.setProject(p);

            //获取该项目的文件列表
            List<Object> fileList = fileService.getFileList(p.getProjectId()).getData();
            respProjectInfo.setProjectfileList(fileList);

            //获取该项目的成员列表
            List<Student> studentList = projectService.getAllMemberById(p.getProjectId());
            respProjectInfo.setMemberList(studentList);

            //遍历阶段列表phaseList获得每个阶段的任务列表
            Iterator iterator1 = phaseList.iterator();
            while(iterator1.hasNext()){
                Phase ph = (Phase)iterator1.next();
                RespBody task = taskService.display(studentId, p.getProjectId(), ph.getPhaseId());
                List<Object> taskList = task.getData();

                //构造阶段信息
                RespPhaseInfo respPhaseInfo = new RespPhaseInfo();
                respPhaseInfo.setPhase(ph);
                respPhaseInfo.setTasks(taskList);
                respProjectInfo.getPhaseInfos().add(respPhaseInfo);
            }
            respUserInfo.getProjects().add(respProjectInfo);
        }
        return RespUser.build(respUserInfo);
    }
}
