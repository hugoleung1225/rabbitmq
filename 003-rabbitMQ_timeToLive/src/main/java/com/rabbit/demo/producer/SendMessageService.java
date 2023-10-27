package com.rabbit.demo.producer;

import com.rabbit.demo.RabbitmqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class SendMessageService implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {
    private static Logger logger = LoggerFactory.getLogger(SendMessageService.class);

    @Autowired
    public RabbitTemplate rabbitTemplate;

    public void sendMessageWithQueueDelay(String str){
        System.out.println(" Send message with queue delay setup ");
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.setConfirmCallback(this);

        //message with id , default with queue delay
        Message msg = MessageBuilder.withBody((str).getBytes()).setMessageId(UUID.randomUUID()+"").build();

        // CorrelationData构造函数中的id可以随便写，但是必须要非null而且是唯一的
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TEST,RabbitmqConfig.ROUTE_DELAY, msg,new CorrelationData(UUID.randomUUID().toString()));
    }

    public void sendDelayMessage(String str){
        System.out.println(" Send delay message.");
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.setConfirmCallback(this);

       //message with message delay time
        Message msg = MessageBuilder.withBody(
                        (str).getBytes())
                .setMessageId(UUID.randomUUID()+"")
                .setExpiration("4000")
                .build();
        // CorrelationData构造函数中的id可以随便写，但是必须要非null而且是唯一的
        rabbitTemplate.convertAndSend(RabbitmqConfig.EXCHANGE_TEST,RabbitmqConfig.ROUTE_TEST, msg,new CorrelationData(UUID.randomUUID().toString()));
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        System.out.println("sender return success" + returnedMessage.toString());
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        if (!b) {
            logger.error("消息发送异常!");
            // 进行处理
        } else {
            logger.info("发送者已经收到确认，correlationData={} ,ack={}, cause={}", correlationData.getId(), b, s);
        }
    }
}

