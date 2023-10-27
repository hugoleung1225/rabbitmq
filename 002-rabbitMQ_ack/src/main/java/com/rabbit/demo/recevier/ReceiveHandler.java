package com.rabbit.demo.recevier;

import com.rabbit.demo.config.RabbitmqConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Breaker-93
 * @date 2021/8/25 13:39
 */
@Service
public class ReceiveHandler {
    /**
     * 队列监听
     *
     * @param message
     * @param message
     * @param deliveryTag
     */
    //@RabbitListener(queues = RabbitmqConfig.QUEUE_TEST)
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitmqConfig.QUEUE_TEST, durable = "true", autoDelete = "false"),
            exchange = @Exchange(value = RabbitmqConfig.EXCHANGE_TEST, ignoreDeclarationExceptions = "true",  type = ExchangeTypes.DIRECT), key = RabbitmqConfig.ROUTE_TEST))
    public void consumer(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try{
            String messageId = message.getMessageProperties().getMessageId();

            System.out.printf(" messageId=%s , deliveryTag=%s \n",messageId,deliveryTag);

            String msgContent = new String(message.getBody(),"utf-8");

            System.out.println("!!!!!!consumer msg: " + msgContent);
            System.out.println("!!!!!!Send ack to exchange!!!!!! " );
            channel.basicAck(deliveryTag,true);

        }catch (Exception e){

            e.printStackTrace();

            /**
             * 有异常就绝收消息
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * requeue:true为将消息重返当前消息队列,还可以重新发送给消费者;
             *         false:将消息丢弃
             */

            // long deliveryTag, boolean multiple, boolean requeue

            try {

                channel.basicNack(deliveryTag,false,true);
                // long deliveryTag, boolean requeue
                // channel.basicReject(deliveryTag,true);

                Thread.sleep(1000);     // 这里只是便于出现死循环时查看

                /*
                 * 一般实际异常情况下的处理过程：记录出现异常的业务数据，将它单独插入到一个单独的模块，
                 * 然后尝试3次，如果还是处理失败的话，就进行人工介入处理
                 */

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
