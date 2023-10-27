package com.rabbit.demo.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;

public class RabbitTemplate {
    @Bean
    public org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        org.springframework.amqp.rabbit.core.RabbitTemplate rabbitTemplate = new org.springframework.amqp.rabbit.core.RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        //确认消息送到交换机(Exchange)回调
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("\n确认消息送到交换机(Exchange)结果：");
            System.out.println("相关数据：" + correlationData);
            System.out.println("是否成功：" + ack);
            System.out.println("错误原因：" + cause);
        });

        //确认消息送到队列(Queue)回调
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            System.out.println("\n确认消息送到队列(Queue)结果：");
            System.out.println("发生消息：" + returnedMessage.getMessage());
            System.out.println("回应码：" + returnedMessage.getReplyCode());
            System.out.println("回应信息：" + returnedMessage.getReplyText());
            System.out.println("交换机：" + returnedMessage.getExchange());
            System.out.println("路由键：" + returnedMessage.getRoutingKey());
        });
        return rabbitTemplate;
    }
}
