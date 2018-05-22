package com.ec.controller;

import com.ec.controller.resp.RespBody;
import com.ec.controller.resp.RespJson;
import com.ec.entity.Task;
import com.ec.service.TaskService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    /**
     * 在阶段下新建任务
     */
    @RequestMapping("/createTask")
    public void createTask (HttpServletRequest request, String callback,HttpServletResponse response)throws Exception{

        //获取参数
        Integer userId = Integer.parseInt(request.getParameter("userId"));
        Integer studentId = Integer.parseInt(request.getParameter("studentId"));
        Integer projectId = Integer.parseInt(request.getParameter("projectId"));
        Integer phaseId = Integer.parseInt(request.getParameter("phaseId"));
        String tTitle = request.getParameter("tTitle");
        String tDescription = request.getParameter("tDescription");
        String deadline=request.getParameter("deadline");
        Integer workload = Integer.parseInt(request.getParameter("workload"));


        //向service层发出修改请求
        RespBody respTask = taskService.createTask(userId,studentId,projectId,phaseId,tTitle,tDescription,deadline,workload);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respTask));
    }


    @RequestMapping("/displayTask")
    public void display(HttpServletRequest request, String callback,HttpServletResponse response)throws Exception {

        //获取参数
        String studentId=request.getParameter("studentId");
        int i = Integer.parseInt(studentId);

        //向service层发出查询请求
        RespBody respTask = taskService.display(i);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respTask));

    }
    @RequestMapping("/displayTaskByProj")
    public void displayByProj(HttpServletRequest request, String callback, HttpServletResponse response)throws Exception {

        String studentId=request.getParameter("studentId");
        String projectId=request.getParameter("ProjectId");
        int i = Integer.parseInt(studentId);
        int j=Integer.parseInt(projectId);
        List<Task> taskList = taskService.display(i,j);

        if(taskList.isEmpty()) {
            response.getWriter().print(callback+"("+"{\"state\""+":"+"\"false\"}"+")");
        }else {
            if(callback==null){
                response.getWriter().print(JSONObject.fromObject(taskList.get(0)).toString());//非异域请求
            }else {
                response.getWriter().print(callback+"("+JSONObject.fromObject(taskList.get(0)).toString()+")");
            }
        }
    }

    @RequestMapping("/commitTask")
    public void commitTask(HttpServletResponse response,HttpServletRequest request,String callback)throws Exception{
        //获取参数
        int userId = Integer.parseInt(request.getParameter("userId"));
        int taskId = Integer.parseInt(request.getParameter("taskId"));

        RespBody respBody = taskService.updateTaskCommit(userId,taskId);

        response.getWriter().print(RespJson.ifJsonp(callback ,respBody));

    }
}
