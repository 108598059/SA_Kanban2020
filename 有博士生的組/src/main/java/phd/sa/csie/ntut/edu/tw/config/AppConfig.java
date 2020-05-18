package phd.sa.csie.ntut.edu.tw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.MysqlCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.read.GetColumnsByBoardIDUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.CardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;


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
        return new CreateBoardUseCase(getBoardRepository());
    }

    @Bean
    public CreateCardUseCase getCreateCardUseCase() {
        DomainEventBus eventBus = getDomainEventBus();
        BoardRepository boardRepo = getBoardRepository();
        CardRepository cardRepo = getCardRepository();
        eventBus.register(new CardCreatedEventHandler(cardRepo, boardRepo));
        return new CreateCardUseCase(eventBus, cardRepo, boardRepo);
    }

    @Bean
    public CommitCardUseCase getCommitUsecase() {
        return new CommitCardUseCase(getCardRepository(), getBoardRepository());
    }

    @Bean
    public GetColumnsByBoardIDUseCase getGetColumnsByBoardIDUseCase() {
        return new GetColumnsByBoardIDUseCase(getBoardRepository(), getCardRepository());
    }
}
