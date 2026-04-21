package school.sptech.crud_proj_v1.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String IMPORT_QUEUE = "import_csv_queue";
    public static final String EMAIL_QUEUE = "email_queue";
    public static final String EXCHANGE = "brinks_exchange";
    public static final String IMPORT_ROUTING_KEY = "import.csv";
    public static final String EMAIL_ROUTING_KEY = "email.send";

    @Bean
    public Queue importQueue() {
        return QueueBuilder.durable(IMPORT_QUEUE).build();
    }

    @Bean
    public Queue emailQueue(){
        return QueueBuilder.durable(EMAIL_QUEUE).build();
    }

    @Bean
    public DirectExchange Exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding importBinding() {
        return BindingBuilder
                .bind(importQueue())
                .to(Exchange())
                .with(IMPORT_ROUTING_KEY);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder
                .bind(emailQueue())
                .to(Exchange())
                .with(EMAIL_ROUTING_KEY);
    }
}
