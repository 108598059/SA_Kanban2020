package ddd.kanban.application.config;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DomainEventHandlerConfig {

    @Bean
    public DomainEventHandler DomainEventHandler(WorkflowRepository workflowRepository, BoardRepository boardRepository, DomainEventBus domainEventBus) {
        return new DomainEventHandler(workflowRepository, boardRepository, domainEventBus);
    }
}
