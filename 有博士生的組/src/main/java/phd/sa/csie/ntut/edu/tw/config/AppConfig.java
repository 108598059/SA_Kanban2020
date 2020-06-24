package phd.sa.csie.ntut.edu.tw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.board.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.card.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.enter.EnterBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.card.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

@Configuration
public class AppConfig {
    @Bean
    public CardRepository getCardRepository() {
        return new MysqlCardRepository();
    }

    @Bean
    public BoardRepository getBoardRepository() {
        return new MysqlBoardRepository();
    }

    @Bean
    public DomainEventBus getDomainEventBus() {
        return new DomainEventBus();
    }

    @Bean
    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(getDomainEventBus(), getBoardRepository());
    }

    @Bean
    public CreateCardUseCase getCreateCardUseCase() {
        DomainEventBus eventBus = this.getDomainEventBus();
        BoardRepository boardRepo = this.getBoardRepository();
        CardRepository cardRepo = this.getCardRepository();
        eventBus.register(new CardCreatedEventHandler(eventBus, cardRepo, boardRepo));
        return new CreateCardUseCase(eventBus, cardRepo);
    }

    @Bean
    public CommitCardUseCase getCommitUseCase() {
        return new CommitCardUseCase(this.getDomainEventBus(), this.getCardRepository(), this.getBoardRepository());
    }

    @Bean
    public EnterBoardUseCase getGetColumnsByBoardIDUseCase() {
        return new EnterBoardUseCase(this.getDomainEventBus(), this.getBoardRepository(), this.getCardRepository());
    }
}
