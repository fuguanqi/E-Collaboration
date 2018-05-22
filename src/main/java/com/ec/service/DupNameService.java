package com.ec.service;

public interface DupNameService {

    //用户名在系统中是否重名
    Boolean isDupUserNameInSys(String isStudent,String userName);

    //用户名在项目中是否重名
    Boolean isDupUserNameInPro(Integer projectId,String userName);

    //项目名是否重名
    Boolean isDupProjectName(String projectName);

    //阶段名在项目中是否重名
    Boolean isDupPhaseNameInPro(Integer projectId,String phaseName);

    //任务名在阶段中是否重名
    Boolean isDupTaskNameInPh(Integer projectId,Integer phaseId,String taskName);
}
