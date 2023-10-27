package com.rabbit.demo;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Breaker-93
 * @date 2021/8/25 10:37
 */
@Configuration

public class RabbitmqConfig {
    public static final String QUEUE_TEST = "queue_basic";
    public static final String EXCHANGE_TEST = "exchange_basic";
    public static final String ROUTE_TEST = "basic";

    public static final String QUEUE_DELAY = "queue_delay";
    public static final String ROUTE_DELAY = "queueDelay";

    /**
     * 声明交换机
     *
     * @return
     */
    @Bean(EXCHANGE_TEST)
    public Exchange EXCHANGE_TEST() {
        // durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_TEST).durable(true).build();
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean(QUEUE_TEST)
    public Queue QUEUE_TEST() {
        return new Queue(QUEUE_TEST);
    }

    @Bean(QUEUE_DELAY)
    public Queue QUEUE_DELAY() {

        Map<String, Object> map = new HashMap<>();

        return QueueBuilder.durable(QUEUE_DELAY)
                .ttl(5000)
                .deadLetterExchange("dl.direct")
                .deadLetterRoutingKey("dl")
                .build();
    }


    /**
     * 队列绑定交换机
     */
    @Bean
    public Binding ROUTE_TEST(@Qualifier(QUEUE_TEST) Queue queue, @Qualifier(EXCHANGE_TEST) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTE_TEST).noargs();
    }

    @Bean
    public Binding ROUTE_DELAY(@Qualifier(QUEUE_DELAY) Queue queue, @Qualifier(EXCHANGE_TEST) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTE_DELAY).noargs();
    }
}

