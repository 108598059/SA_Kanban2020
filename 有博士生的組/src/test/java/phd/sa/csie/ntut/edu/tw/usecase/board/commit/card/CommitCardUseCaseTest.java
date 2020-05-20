package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private BoardRepository boardRepository;
    private CardRepository cardRepository;
    private Board board;
    private Card card;

    @Before
    public void setUp() {
        this.board = new Board("Kanban");
        this.boardRepository = new MemoryBoardRepository();
        this.boardRepository.save(BoardDTOConverter.toDTO(this.board));

        this.card = new Card("Card1", this.board);
        this.cardRepository = new MemoryCardRepository();
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));
    }

    @Test
    public void committed_card_should_be_added_to_the_default_column() {
        CommitCardUseCaseInput input = new CommitCardUseCaseInput();
        CommitCardUseCaseOutput output = new CommitCardUseCaseOutput();


        input.setBoardID(this.board.getID().toString());
        input.setCardID(this.card.getID().toString());

        CommitCardUseCase useCase = new CommitCardUseCase(this.cardRepository, this.boardRepository);
        useCase.execute(input, output);

        assertEquals(this.board.getID().toString(), output.getBoardID());
        assertEquals(this.card.getID().toString(), output.getCardID());

        Board resultBoard = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));
        assertEquals(this.card.getID(), resultBoard.get(0).getCardIDs().get(0));
    }
}
