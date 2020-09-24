package com.example.rabbitmqconsumer.boot;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wzx
 */
@Configuration
public class DeadLetterRabbitConfig {

    public static final String LIND_EXCHANGE = "lind.exchange";
    public static final String LIND_DL_EXCHANGE = "lind.dl.exchange";
    public static final String LIND_QUEUE = "lind.queue";
    public static final String LIND_DEAD_QUEUE = "lind.dead.queue";

    /**
     * 创建普通交换机
     * @return
     */
    @Bean
    public DirectExchange lindExchange() {
        return (DirectExchange) ExchangeBuilder.directExchange(LIND_EXCHANGE).durable(true).build();
    }

    /**
     * 创建死信交换机
     * @return
     */
    @Bean
    public DirectExchange lindExchangeDl() {
        return (DirectExchange) ExchangeBuilder.directExchange(LIND_DL_EXCHANGE).durable(true)
                .build();
    }

    /**
     * 创建普通队列.
     */
    @Bean
    public Queue lindQueue() {
        return QueueBuilder.durable(LIND_QUEUE)
                .withArgument("x-dead-letter-exchange", LIND_DL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", LIND_DEAD_QUEUE)
                .build();
    }

    /**
     * 创建死信队列.
     */
    @Bean
    public Queue lindDelayQueue() {
        return QueueBuilder.durable(LIND_DEAD_QUEUE).build();
    }

    /**
     * 绑定死信队列.
     */
    @Bean
    public Binding bindDeadBuilders() {
        return BindingBuilder.bind(lindDelayQueue())
                .to(lindExchangeDl())
                .with(LIND_DEAD_QUEUE);
    }

    /**
     * 绑定普通队列.
     *
     * @return
     */
    @Bean
    public Binding bindBuilders() {
        return BindingBuilder.bind(lindQueue())
                .to(lindExchange())
                .with(LIND_QUEUE);
    }


}
