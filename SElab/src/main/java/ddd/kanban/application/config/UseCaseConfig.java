package ddd.kanban.application.config;

import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.board.get.GetAllBoardsUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Autowired
    private DomainEventBus domainEventBus;

    @Autowired
    private BoardRepository boardRepository;

    @Bean
    public CreateBoardUseCase createBoardUseCase() {
        return new CreateBoardUseCase(boardRepository, domainEventBus);
    }

    @Bean
    public GetAllBoardsUseCase getAllBoardsUseCase() {
        return new GetAllBoardsUseCase(boardRepository);
    }
}
