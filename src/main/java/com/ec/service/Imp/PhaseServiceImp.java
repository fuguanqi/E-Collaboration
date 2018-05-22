package com.ec.service.Imp;

import java.util.ArrayList;
import java.util.Date;

import com.ec.controller.resp.RespBody;
import com.ec.entity.*;
import com.ec.mapper.ParticipateMapper;
import com.ec.mapper.PhaseMapper;
import com.ec.service.MailService;
import com.ec.service.PhaseService;
import com.ec.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class PhaseServiceImp implements PhaseService {

    @Autowired
    private PhaseMapper phaseMapper;
    @Autowired
    private ParticipateMapper participateMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private ProjectService projectService;

    public RespBody createPhase(Integer studentId, Integer projectId, String phTitle, String phDescription) {

        //查看是否有组长权限
        if(!isAccess(projectId,studentId)){
            return RespBody.build(500,"NO PERMISSIONS OR PROJECT NO EXIST",new ArrayList());
        }

        //查看阶段是否重名
        if(isDupName(projectId,phTitle)){
            return RespBody.build(500,"THE DUPLICATION OF NAME",new ArrayList());
        }

        //构造新阶段并插入数据库
        Phase phase = new Phase();
        phase.setProjectId(projectId);
        phase.setPhTitle(phTitle);
        phase.setPhDescription(phDescription);
        phase.setPhBeginTime(new Date());
        int i = phaseMapper.insertSelective(phase);
        if(i==0) {
            return RespBody.build(500,"INTERNAL ERROR",new ArrayList());
        } else {
            PhaseExample phaseExample = new PhaseExample();
            PhaseExample.Criteria criteria1 = phaseExample.createCriteria();
            criteria1.andPhTitleEqualTo(phTitle);
            List<Phase> phases = phaseMapper.selectByExample(phaseExample);

            //通知项目的所有成员
            List<Student> participateList = projectService.getAllMemberById(projectId);
            Iterator iterator = participateList.iterator();
            while(iterator.hasNext()){
                Student s = (Student) iterator.next();
                Integer id = s.getStudentId();
                mailService.sendEmail(id,projectId,"新建阶段","阶段名："+phTitle+",阶段说明："+phDescription);
            }
            return RespBody.build(phases);
        }

    }



    public Boolean isDupName(Integer projectId, String phTitle) {
        PhaseExample phaseExample = new PhaseExample();
        PhaseExample.Criteria criteria1 = phaseExample.createCriteria();
        criteria1.andProjectIdEqualTo(projectId);
        criteria1.andPhTitleEqualTo(phTitle);
        List<Phase> phaseList = phaseMapper.selectByExample(phaseExample);
        if(phaseList.isEmpty()){
            return false;
        }
        return true;
    }

    public Boolean isAccess(Integer projectId, Integer studentId) {
        ParticipateExample participateExample = new ParticipateExample();
        ParticipateExample.Criteria criteria = participateExample.createCriteria();
        criteria.andStudentIdEqualTo(studentId);
        criteria.andProjectIdEqualTo(projectId);
        criteria.andRoleEqualTo(1);//只有组长能新建阶段
        List<Participate> participateList = participateMapper.selectByExample(participateExample);
        if(participateList.isEmpty()){
            return false;
        }
        return true;
    }

    public RespBody findPhase(Integer projectId) {
        PhaseExample phaseExample = new PhaseExample();
        PhaseExample.Criteria  criteria= phaseExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        List<Phase> phases = phaseMapper.selectByExample(phaseExample);
        RespBody respBody = RespBody.build(phases);
        return respBody;
    }
}
