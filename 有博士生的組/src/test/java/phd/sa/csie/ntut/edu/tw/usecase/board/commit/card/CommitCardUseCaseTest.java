package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private Board board;
    private Card card;
    private DomainEventBus eventBus;

    @Before
    public void create_a_card_and_create_a_board() {
        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.boardRepository = new MemoryBoardRepository();
        this.boardRepository.save(BoardDTOConverter.toDTO(this.board));

        this.card = new Card("Card1", this.board);
        this.cardRepository = new MemoryCardRepository();
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));

        this.eventBus = new DomainEventBus();
    }

    @Test
    public void commit_card_should_add_card_to_the_backlog_column () {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(this.eventBus, this.cardRepository, this.boardRepository);
        CommitCardUseCaseInput input = new CommitCardUseCaseInput();
        CommitCardUseCaseOutput output = new CommitCardUseCaseOutput();

        input.setBoardID(this.board.getID().toString());
        input.setCardID(this.card.getID().toString());

        commitCardUseCase.execute(input, output);

        assertEquals(this.board.getID().toString(), output.getBoardID());
        assertEquals(this.card.getID().toString(), output.getCardID());

        Board resultBoard = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
        assertEquals(this.card.getID(), resultBoard.getBacklogColumn().getCardIDs().get(0));
    }
}
