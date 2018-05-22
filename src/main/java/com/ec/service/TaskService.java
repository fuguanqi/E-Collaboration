package com.ec.service;

import com.ec.controller.resp.RespBody;
import com.ec.entity.Task;

import java.util.List;

public interface TaskService {

    //新建任务
    RespBody createTask(Integer userid, Integer studentId, Integer projectId, Integer phaseId, String tTitle, String tDescription, String deadline, Integer workload);

    //查询任务
    RespBody display(Integer studentId);

    RespBody display(Integer studentId,Integer projectId,Integer phaseId);

    List<Task> display(Integer studentId,Integer projectId);

    RespBody updateTaskCommit(int userId, int taskId);

}
