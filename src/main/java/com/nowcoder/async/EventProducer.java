package com.nowcoder.async;

import com.alibaba.fastjson.JSON;
import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by luotj on 2017/5/13.
 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {
        try {
            String json = JSON.toJSONString(model);
            String key = RedisKeyUtil.getEventKey();
            jedisAdapter.lpush(key,json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
