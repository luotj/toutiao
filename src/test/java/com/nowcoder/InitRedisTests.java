package com.nowcoder;

import com.nowcoder.model.User;
import com.nowcoder.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)

public class InitRedisTests {

    @Autowired
    JedisAdapter jedisAdapter;

    @Test
    public void initData() {
        User user = new User();
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", 1));
        user.setName(String.format("USER%d", 1));
        user.setPassword("zzz");
        user.setSalt("xxx");
        jedisAdapter.setObject("user1",user);
        User user1 = jedisAdapter.getObject("user1",User.class);
        System.out.println(user1.getHeadUrl()+user1.getName());
    }

}
