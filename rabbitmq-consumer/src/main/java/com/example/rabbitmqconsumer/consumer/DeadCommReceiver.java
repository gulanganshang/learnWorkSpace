package com.example.rabbitmqconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;

/**
 * @author wzx
 * 监听普通队列
 */
@Component
@RabbitListener(queues = "lind.queue")
public class DeadCommReceiver {

    @RabbitHandler
    public void process(Map testMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("测试死信的普通队列的消息  : " + testMessage.toString());
        System.out.println("将消息拒绝放回队列中");
        channel.basicReject(deliveryTag, false);
    }

}
