package com.nowcoder.async;

import com.alibaba.fastjson.JSON;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by luotj on 2017/5/13.
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private ApplicationContext applicationContext;
    private Map<EventType,List<EventHandler>> config = new HashedMap();

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String,EventHandler> entry : beans.entrySet()) {
                for (EventType eventType : entry.getValue().getSupportEventTypes()) {
                    if (!config.containsKey(eventType)) {
                        config.put(eventType, new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());
                }
            }
        }
        System.out.println("config:  " + JSON.toJSONString(config));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String key = RedisKeyUtil.getEventKey();
                    List<String> events = jedisAdapter.brpop(0,key);
                    for (String message : events) {
                        if(message.equals(key)) {
                            continue;
                        }

                        EventModel eventModel = JSON.parseObject(message, EventModel.class);

                        if (!config.containsKey(eventModel.getType())) {
                            logger.error(message + "事件未注册！");
                            continue;
                        }

                        for (EventHandler handler : config.get(eventModel.getType())) {
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });

        thread.start();

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
