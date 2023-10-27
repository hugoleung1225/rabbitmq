package com.rabbit.demo;


import com.rabbit.demo.config.RabbitmqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    RabbitTemplate rabbitTemplate;

    @Test
    @Description("Send a messsage to topic.")
    public void Producer_topics_springbootTest() {

        // 使用rabbitTemplate发送消息
        String message = "Hello, rabbit mq";
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TEST, "basic", message);

    }
}

