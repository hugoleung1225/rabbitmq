package com.rabbit.demo;


import com.rabbit.demo.producer.SendMessageService;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

/**
 * @author Breaker-93
 * @date 2021/8/25 12:41
 */

@SpringBootTest(classes = DemoApplication.class )
public class ProducerTest {

    @Autowired
    private SendMessageService sendMessageService;
    @Test
    @Description("Send a delay messsage to topic.")
    public void Producer_topics_springbootTest() {

        // 使用rabbitTemplate发送消息
        String message = "Hello, rabbit mq";
        sendMessageService.sendDelayMessage(message);

    }

    @Test
    @Description("Send messsage with delay queue setup.")
    public void Producer_delayQueue() {

        // 使用rabbitTemplate发送消息
        String message = "Hello, rabbit mq";
        sendMessageService.sendMessageWithQueueDelay(message);

    }
}

