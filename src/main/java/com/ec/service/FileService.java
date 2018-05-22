package com.ec.service;

import com.ec.controller.resp.RespBody;
import com.ec.entity.Projectfile;

public interface FileService {

    //将上传文件的记录保存到数据库
    public int createProjectflie(Projectfile f);

    public Projectfile getFileById(int fileId);

    public RespBody getFileList(int projectId);
}
