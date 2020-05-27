package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

public class SetColumnWIPUseCase extends UseCase<SetColumnWIPUseCaseInput, SetColumnWIPUseCaseOutput> {
    private BoardRepository boardRepository;

    public SetColumnWIPUseCase(DomainEventBus eventBus, BoardRepository boardRepository) {
        super(eventBus);
        this.boardRepository = boardRepository;
    }

    public void execute(SetColumnWIPUseCaseInput input, SetColumnWIPUseCaseOutput output) {
        String boardID = input.getBoardID();
        String columnID = input.getColumnID();
        int wip = input.getColumnWIP();

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID));
        board.setColumnWIP(UUID.fromString(columnID), wip);

        this.boardRepository.update(BoardDTOConverter.toDTO(board));
        this.eventBus.postAll(board);

        output.setColumnID(columnID);
        output.setColumnWIP(wip);
    }

}