package com.backend.last_stand.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * rabbitmq配置类
 */
@Component
public class RabbitConfig {

    /**
     * 定义消息转换实例 ，转化成 JSON传输
     *
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    public MessageConverter integrationEventMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * 配置启用rabbitmq事务
     *
     * @param connectionFactory connectionFactory
     * @return RabbitTransactionManager
     */
    @Bean
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}
