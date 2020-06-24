package ddd.kanban.application.config;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.kanbanboard.board.get.GetAllBoardsUseCase;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateBoardUseCase createBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus, DomainEventHandler domainEventHandler) {
        domainEventBus.register(domainEventHandler);
        return new CreateBoardUseCase(boardRepository, domainEventBus);
    }

    @Bean
    public GetAllBoardsUseCase getAllBoardsUseCase(BoardRepository boardRepository) {
        return new GetAllBoardsUseCase(boardRepository);
    }
}
