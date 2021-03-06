package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class CommitCardUseCase extends UseCase<CommitCardUseCaseInput, CommitCardUseCaseOutput> {
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;

    public CommitCardUseCase(DomainEventBus eventBus, CardRepository cardRepository, BoardRepository boardRepository) {
        super(eventBus);
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    public void execute(CommitCardUseCaseInput input, CommitCardUseCaseOutput output) {
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(input.getCardID()));

        board.commitCard(card, board.get(0).getID());

        this.boardRepository.update(BoardDTOConverter.toDTO(board));
        this.eventBus.postAll(board);

        output.setBoardID(board.getID().toString());
        output.setCardID(card.getID().toString());
    }
}
