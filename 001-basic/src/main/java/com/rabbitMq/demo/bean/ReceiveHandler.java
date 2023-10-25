package com.rabbitMq.demo.bean;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;

import com.rabbitMq.demo.config.RabbitmqConfig;
import org.springframework.stereotype.Service;

/**
 * @author Breaker-93
 * @date 2021/8/25 13:39
 */
@Service
public class ReceiveHandler {
    /**
     * 队列监听
     *
     * @param msg
     * @param message
     * @param channel
     */
    //@RabbitListener(queues = RabbitmqConfig.QUEUE_TEST)
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitmqConfig.QUEUE_TEST, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = RabbitmqConfig.EXCHANGE_TEST, ignoreDeclarationExceptions = "true",  type = ExchangeTypes.DIRECT), key = RabbitmqConfig.ROUTE_TEST))
    public void consumer(String msg) {

        System.out.println("!!!!!!consumer msg: " + msg);
    }
}
