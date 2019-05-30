package com.promotion.aggregate.configure;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.Assert;

import com.tm.core.dto.RabbitMQConfigData;

@Configuration
@PropertySource(value = "classpath:rabbitmq.properties")
public class RabbitMQConfig {

    public static final String QUEUE_NAME = "RULE_ENGINE_QUEUE";
    public static final String DEAD_LETTER_QUEUE_NAME = "RULE_ENGINE_DEAD_QUEUE";

    public static final String DELAY_EXCHANGE_NAME = "RULE_ENGINE_DELAY_EXCHANGE";
    public static final String DIRECT_EXCHANGE_NAME = "RULE_ENGINE_EXCHANGE";

    private static final Integer MAX_MESSAGE_IN_QUEUE = 1000000;
    private static final Integer MAX_MESSAGE_LENGTH = MAX_MESSAGE_IN_QUEUE * 512;
    private static final Integer MAX_PRIORITY_LEVEL = 3;
    private static final Integer TIMEOUT_VALUE = 20000;
    
    public static final RabbitMQConfigData CONFIG_DATA = new RabbitMQConfigData(
    		DIRECT_EXCHANGE_NAME, 
    		QUEUE_NAME);

    @Value("${rabbitmq.host}")
    private String RABBITMQ_HOST;
    
    @Value("${rabbitmq.port}")
    private Integer RABBITMQ_PORT;

    @Value("${rabbitmq.username}")
    private String RABBITMQ_USERNAME;

    @Value("${rabbitmq.password}")
    private String RABBITMQ_PASSWORD;

    @Bean
    public ConnectionFactory connectionFactory() {
		Assert.notNull(RABBITMQ_HOST, "rabbitmq host cannot be null");		//prevent default is localhost
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory(RABBITMQ_HOST, RABBITMQ_PORT);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
		connectionFactory.setConnectionTimeout(TIMEOUT_VALUE);
		connectionFactory.createConnection().close();						//just in case host/port is unreachable
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public CustomExchange delayExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");

        return new CustomExchange(DELAY_EXCHANGE_NAME, "x-delayed-message", true, false, arguments);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(DIRECT_EXCHANGE_NAME);
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> deadQueueArguments = new HashMap<>();
        deadQueueArguments.put("x-max-length", MAX_MESSAGE_IN_QUEUE);
        deadQueueArguments.put("x-max-length-bytes", MAX_MESSAGE_LENGTH);
        deadQueueArguments.put("x-max-priority", MAX_PRIORITY_LEVEL);

        return new Queue(DEAD_LETTER_QUEUE_NAME, true, false, false, deadQueueArguments);
    }

    @Bean
    public Queue queue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DIRECT_EXCHANGE_NAME);
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_NAME);
        arguments.put("x-max-length", MAX_MESSAGE_IN_QUEUE);
        arguments.put("x-max-length-bytes", MAX_MESSAGE_LENGTH);
        arguments.put("x-max-priority", MAX_PRIORITY_LEVEL);

        return new Queue(QUEUE_NAME, true, false, false, arguments);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(QUEUE_NAME);
    }

    @Bean
    public Binding delayQueueBinding(Queue queue, CustomExchange delayExchange) {
        return BindingBuilder.bind(queue).to(delayExchange).with(QUEUE_NAME).noargs();
    }

    @Bean
    public Binding bindingDeadLetter(Queue deadLetterQueue, TopicExchange exchange) {
        return BindingBuilder.bind(deadLetterQueue).to(exchange).with(DEAD_LETTER_QUEUE_NAME);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new JsonMessageConverter();
    }

}