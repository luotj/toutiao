package com.nowcoder.async;

import java.util.List;

/**
 * Created by luotj on 2017/5/13.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportEventTypes();
}
