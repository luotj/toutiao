package com.nowcoder.util;

/**
 * Created by luotj on 2017/5/12.
 * 这个类用来管理Redis中的key，在应用开发过程中key的管理是很重要的，用这个工具类来规范key的命名
 */

public class RedisKeyUtil {
    private static String SPLIT = ":";
    private static String BIZ_LIKE = "LIKE";
    private static String BIZ_DISLIKE = "DISLIKE";
    private static String BIZ_EVENT = "EVENT";

    public static String getEventKey() {
        return BIZ_EVENT;
    }

    public static String getLikeKey(int EntityId, int EntityType) {
        return BIZ_LIKE+SPLIT+String.valueOf(EntityId)+SPLIT+String.valueOf(EntityId);
    }

    public static String getDisLikeKey(int EntityId, int EntityType) {
        return BIZ_DISLIKE+SPLIT+String.valueOf(EntityId)+SPLIT+String.valueOf(EntityId);
    }


}
