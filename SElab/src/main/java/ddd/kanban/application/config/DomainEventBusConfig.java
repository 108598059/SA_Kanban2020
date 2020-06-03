package ddd.kanban.application.config;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.handler.DomainEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainEventBusConfig {


    @Bean
    public DomainEventBus domainEventBus() {
        return new DomainEventBus();
    }
}
