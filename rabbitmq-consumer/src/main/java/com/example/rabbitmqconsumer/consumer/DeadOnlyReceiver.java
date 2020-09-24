package com.example.rabbitmqconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author wzx
 * 监听死信普通队列
 */
@Component
@RabbitListener(queues = "lind.dead.queue")
public class DeadOnlyReceiver {

    @RabbitHandler
    public void process(Map testMessage, Channel channel) throws IOException {
        System.out.println("测试死信队列的消息  : " + testMessage.toString());
        channel.basicAck(1, false);
    }

}
