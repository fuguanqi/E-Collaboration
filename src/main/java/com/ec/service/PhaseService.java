package com.ec.service;

import com.ec.controller.resp.RespBody;

public interface PhaseService {
    //在项目下新建阶段
    RespBody createPhase(Integer studentId, Integer projectId, String phTitle, String phDescription);

    //是否有重名
    Boolean isDupName(Integer projectId,String phTitle);

    //是否有组长权限
    Boolean isAccess(Integer projectId,Integer studentId);

    RespBody findPhase(Integer projectId);

}
