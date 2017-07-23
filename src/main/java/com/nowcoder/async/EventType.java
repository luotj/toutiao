package com.nowcoder.async;

/**
 * Created by luotj on 2017/5/13.
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
