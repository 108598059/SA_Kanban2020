package phd.sa.csie.ntut.edu.tw.usecase.column.create;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

public class CreateColumnUseCase extends UseCase<CreateColumnUseCaseInput, CreateColumnUseCaseOutput> {
    private BoardRepository boardRepository;

    public CreateColumnUseCase(DomainEventBus eventBus, BoardRepository boardRepository) {
        super(eventBus);
        this.boardRepository = boardRepository;
    }

    public void execute(CreateColumnUseCaseInput input,
                        CreateColumnUseCaseOutput output) {
        String columnTitle = input.getColumnTitle();
        String boardID = input.getBoardID();

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID));
        UUID columnID = board.createColumn(columnTitle, input.getColumnIndex());

        this.boardRepository.update(BoardDTOConverter.toDTO(board));
        this.eventBus.postAll(board);

        output.setID(columnID.toString());
    }
}