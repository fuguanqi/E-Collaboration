package com.ec.service.Imp;

import com.ec.entity.Message;
import com.ec.entity.MessageExample;
import com.ec.mapper.MessageMapper;
import com.ec.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;
    public int sendMessage(int projectId, String senderName, String content) {
        Message message = new Message();
        message.setProjectId(projectId);
        message.setSendername(senderName);
        message.setContent(content);
        message.setTime(new Date());
        return messageMapper.insert(message);
    }
}
