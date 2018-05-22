package com.ec.controller;

import com.ec.controller.resp.RespUser;
import com.ec.entity.Instructor;
import com.ec.entity.Student;
import com.ec.entity.StudentExample;
import com.ec.jwt.Jwt;
import com.ec.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ec.controller.resp.RespJson;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin//允许跨域请求
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登陆
     */
    @RequestMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response, String callback)throws Exception{

        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String isStudent = request.getParameter("isStudent");

        //Cookie cookie = new Cookie("name","liuxi");
        //response.addCookie(cookie);
        //调试输出
        System.out.println("UserController正在处理登陆请求，登陆信息："+username+":"+password+":"+isStudent);//参数测试

        //向service层发出查询请求
        RespUser respUser = userService.findUser(username,password,isStudent);

        if(respUser.getStatus()==200){
            //生成token
            Map<String , Object> payload=new HashMap<String, Object>();
            Date date=new Date();
//            payload.put("uid", username);//用户名
            Object data=respUser.getData();
            if(isStudent.equals("true")){
                Student stu=(Student) data;
                payload.put("uid", stu.getStudentId());//学生id
            }
            else{
                Instructor ins=(Instructor)data;
                payload.put("uid", ins.getInstructorId());//导师
            }
            payload.put("isStudent", isStudent);//是否学生
            payload.put("iat", date.getTime());//生成时间
            payload.put("ext",date.getTime()+1000*60*60);//过期时间1小时
            String token= Jwt.createToken(payload);
            respUser.setToken(token);
            System.out.println("token="+token);
            System.out.println("respUser="+respUser);

        }

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respUser));
    }

    /**
     * 用户注册
     */
    @RequestMapping("/register")
    public void register(HttpServletRequest request, String callback,HttpServletResponse response)throws Exception{

        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String isStudent = request.getParameter("isStudent");

        //向service层发出插入请求
        RespUser respUser = userService.registerUser(username,password,isStudent,email);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respUser));
    }

    /**
     * 用户信息修改
     */
    @RequestMapping("/repair")
    public void repairStudent(HttpServletRequest request, String callback,HttpServletResponse response)throws Exception{

        //获取参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String isStudent = request.getParameter("isStudent");

        //向service层发出修改请求
        RespUser respUser = userService.repairUser(username,password,isStudent,email);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respUser));
    }

    /**
     * 用户信息修改
     */
    @RequestMapping("/findAllUserInfo")
    public void findAllUserInfo(HttpServletRequest request, String callback,HttpServletResponse response)throws Exception{

        //获取参数
        String studentId = request.getParameter("studentId");
        int i = Integer.parseInt(studentId);

        //向service层发出修改请求
        RespUser respUser = userService.findAllUserInfo(i);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respUser));
    }
}
