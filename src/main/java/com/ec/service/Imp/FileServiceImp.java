package com.ec.service.Imp;

import com.ec.controller.resp.RespBody;
import com.ec.controller.resp.RespJson;
import com.ec.entity.Project;
import com.ec.entity.ProjectExample;
import com.ec.entity.Projectfile;
import com.ec.entity.ProjectfileExample;
import com.ec.mapper.ProjectfileMapper;
import com.ec.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImp implements FileService {

    @Autowired
    ProjectfileMapper projectfileMapper;

    public int createProjectflie(Projectfile f) {
        int t=projectfileMapper.insert(f);
        if (t==0)
            return 0;
        return t;
    }

    public Projectfile getFileById(int fileId) {

        return projectfileMapper.selectByPrimaryKey(fileId);
    }

    public RespBody getFileList(int projectId) {
        ProjectfileExample projectfileExample = new ProjectfileExample();
        ProjectfileExample.Criteria criteria = projectfileExample.createCriteria();
        criteria.andProjectIdEqualTo(projectId);
        List<Projectfile> projectfiles = projectfileMapper.selectByExample(projectfileExample);
        return RespBody.build(projectfiles);
    }


}
