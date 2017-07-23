package com.nowcoder.service;

import com.nowcoder.util.JedisAdapter;
import com.nowcoder.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 喜欢返回1，不喜欢-1，否则0
 * Created by luotj on 2017/5/12.
 */
@Service
public class LikeService {

    @Autowired
    JedisAdapter jedisAdapter;

    public int getLikeStatus(int userId, int entityId, int entityType) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            return 1;
        }

        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId))) {
            return -1;
        }
        return 0;
    }

    public long like(int userId, int entityId, int entityType) {
        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        jedisAdapter.sadd(likeKey, String .valueOf(userId));

        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(dislikeKey, String.valueOf(userId))) {
            jedisAdapter.srem(dislikeKey, String.valueOf(userId));
        }
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityId, int entityType) {
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId,entityType);
        jedisAdapter.sadd(dislikeKey, String .valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityId,entityType);
        if(jedisAdapter.sismember(likeKey, String.valueOf(userId))) {
            jedisAdapter.srem(likeKey, String.valueOf(userId));
        }
        return jedisAdapter.scard(likeKey);
    }
}
