package com.rabbit.demo;


import com.rabbit.demo.config.RabbitTemplate;
import com.rabbit.demo.config.RabbitmqConfig;
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
    @Description("Send a messsage to topic.")
    public void Producer_topics_springbootTest() {

        // 使用rabbitTemplate发送消息
        String message = "Hello, rabbit mq";
        sendMessageService.sendMessage(message);

    }
}

