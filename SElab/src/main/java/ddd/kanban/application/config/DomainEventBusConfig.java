package ddd.kanban.application.config;

import ddd.kanban.domain.model.DomainEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventBusConfig {

    @Bean
    public DomainEventBus DomainEventBusFactory() {
        return  new DomainEventBus();
    }
}
