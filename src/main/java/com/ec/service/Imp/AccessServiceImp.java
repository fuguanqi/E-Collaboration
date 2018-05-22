package com.ec.service.Imp;

import com.ec.entity.Participate;
import com.ec.entity.ParticipateExample;
import com.ec.mapper.ParticipateMapper;
import com.ec.service.AccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccessServiceImp implements AccessService {
    @Autowired
    ParticipateMapper participateMapper;

    /**
     * @param projectId
     * @param userId
     * @return
     */
    public Boolean isAccessToPro(Integer projectId, Integer userId) {
        ParticipateExample participateExample = new ParticipateExample();
        ParticipateExample.Criteria criteria = participateExample.createCriteria();
        criteria.andStudentIdEqualTo(userId).andProjectIdEqualTo(projectId).andRoleEqualTo(1);//只有组长能新建阶段
        List<Participate> participateList = participateMapper.selectByExample(participateExample);
        if(participateList==null||participateList.isEmpty()){
            return false;
        }
        return true;
    }
}
