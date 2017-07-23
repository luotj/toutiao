package com.nowcoder;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ToutiaoApplication.class)

public class ThreadTests {

    BlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(100);
    public static void main(String[] args) {

    }

}
