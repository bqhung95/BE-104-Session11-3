package com.example.pharmacy.config;

import com.example.pharmacy.subscriber.AlertSubscriber;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisPubSubConfig {

    public static final String CHANNEL = "pharmacy-alerts";

    @Bean
    public RedisMessageListenerContainer redisContainer(
            RedisConnectionFactory connectionFactory,
            AlertSubscriber subscriber) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // MessageListenerAdapter sẽ gọi subscriber.onMessage(String)
        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber, "onMessage");
        // Đảm bảo dùng StringRedisSerializer cho cả message body
        adapter.setSerializer(new org.springframework.data.redis.serializer.StringRedisSerializer());

        container.addMessageListener(adapter, new ChannelTopic(CHANNEL));
        return container;
    }
}