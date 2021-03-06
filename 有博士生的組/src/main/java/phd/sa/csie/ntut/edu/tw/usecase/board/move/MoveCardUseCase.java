package phd.sa.csie.ntut.edu.tw.usecase.board.move;

import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

import java.util.UUID;

public class MoveCardUseCase extends UseCase<MoveCardUseCaseInput, MoveCardUseCaseOutput> {
    private BoardRepository boardRepository;

    public MoveCardUseCase(DomainEventBus eventBus, BoardRepository boardRepository) {
        super(eventBus);
        this.boardRepository = boardRepository;
    }

    public void execute(MoveCardUseCaseInput input, MoveCardUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
        String cardID = input.getCardID();
        String fromColumnID = input.getFromColumnID();
        String toColumnID = input.getToColumnID();

        String newColumnID = board.moveCard(UUID.fromString(cardID),
                UUID.fromString(fromColumnID),
                UUID.fromString(toColumnID));

        this.boardRepository.update(BoardDTOConverter.toDTO(board));
        this.eventBus.postAll(board);

        output.setCardID(cardID);
        output.setFromColumnID(fromColumnID);
        output.setToColumnID(newColumnID);
    }

}
