package com.ec.service;

public interface AccessService {
    /**
     * @param projectId
     * @param userId
     * @return
     */
    //是否具有操作项目的权限
    Boolean isAccessToPro(Integer projectId,Integer userId);
}
