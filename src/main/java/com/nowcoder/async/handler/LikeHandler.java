package com.nowcoder.async.handler;

import com.nowcoder.async.EventHandler;
import com.nowcoder.async.EventModel;
import com.nowcoder.async.EventType;
import com.nowcoder.model.Message;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by luotj on 2017/5/13.
 */
@Component
public class LikeHandler implements EventHandler{
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(eventModel.getActorId());
        message.setToId(eventModel.getEntityOwnerId());
        message.setContent(eventModel.getActorId() + "赞了你的NEWS！");
        message.setCreatedDate(new Date());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
