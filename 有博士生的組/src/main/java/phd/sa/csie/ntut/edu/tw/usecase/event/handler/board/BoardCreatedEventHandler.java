package phd.sa.csie.ntut.edu.tw.usecase.event.handler.board;

import com.google.common.eventbus.Subscribe;
import phd.sa.csie.ntut.edu.tw.model.board.event.create.BoardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.DomainEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

public class BoardCreatedEventHandler implements DomainEventHandler<BoardCreatedEvent> {
    private DomainEventBus eventBus;
    private BoardRepository boardRepository;

    public BoardCreatedEventHandler(DomainEventBus eventBus, BoardRepository boardRepository) {
        this.eventBus = eventBus;
        this.boardRepository = boardRepository;
    }

    @Subscribe
    @Override
    public void listen(BoardCreatedEvent boardCreatedEvent) {
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);
        CreateColumnUseCaseInput input = new CreateColumnUseCaseInput();

        input.setBoardID(boardCreatedEvent.getSourceID());
        input.setColumnTitle("Backlog");
        input.setColumnIndex(0);

        createColumnUseCase.execute(input, new CreateColumnUseCaseOutput());

        input.setColumnTitle("Archive");
        input.setColumnIndex(1);

        createColumnUseCase.execute(input, new CreateColumnUseCaseOutput());
    }
}
