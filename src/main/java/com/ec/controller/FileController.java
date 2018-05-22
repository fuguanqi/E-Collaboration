package com.ec.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ec.controller.resp.RespBody;
import com.ec.controller.resp.RespJson;
import com.ec.entity.Projectfile;
import com.ec.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@Controller
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody
    public String upload(Projectfile f,MultipartFile file, HttpServletRequest request,HttpServletResponse response) throws IOException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date d =new Date();
        f.setUploadTime(d);

        String res = sdf.format(d);

        // uploads文件夹位置
        String rootPath = request.getSession().getServletContext().getRealPath("resource/uploads/"+f.getProjectId()+"/");

        // 原始名称
        String originalFileName = file.getOriginalFilename();
        f.setFileTitle(originalFileName);

        // 新文件名
        String newFileName = "ec" + res + originalFileName.substring(originalFileName.lastIndexOf("."));

        // 创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1));

        // 新文件
        File newFile = new File(rootPath + File.separator + dateDirs + File.separator + newFileName);
        f.setFileUrl(newFile.toString());

        // 判断目标文件所在目录是否存在
        if( !newFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }

        // 将内存中的数据写入磁盘
        file.transferTo(newFile);

        //将上传文件信息写入数据库
        int t = fileService.createProjectflie(f);
        if(t == 0)
            return RespJson.FALSE;
        return RespJson.SUCCESS;

        // 完整的url
        //String fileUrl = date.get(Calendar.YEAR) + "/" + (date.get(Calendar.MONTH)+1) + "/" + newFileName;
    }

    @RequestMapping("/down")
    public void down(HttpServletRequest request,HttpServletResponse response) throws Exception{

        //获得要下载文件的id值
        int fileId = Integer.parseInt( request.getParameter("fileId"));

        //向Service层发送请求，获得目标下载文件对象
        Projectfile downFile = fileService.getFileById(fileId);

        //创建文件输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(downFile.getFileUrl())));

        //设置默认下载后的文件名
        String filename = downFile.getFileTitle();
        filename = URLEncoder.encode(filename,"UTF-8");

        //响应设置
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        response.setContentType("multipart/form-data");

        //创建输出流
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

        //输出文件
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        bis.close();
        out.close();
    }

    @RequestMapping("/getFileList")
    public void getFileList(HttpServletRequest request,HttpServletResponse response,String callback) throws Exception{
        //获取参数
        int projectId = Integer.parseInt(request.getParameter("projectId"));

        //向Service层发送请求
        RespBody respBody = fileService.getFileList(projectId);

        //返回结果
        response.getWriter().print(RespJson.ifJsonp(callback ,respBody));

    }
}