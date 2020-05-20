package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditCardColumnUseCaseTest {
    private CardRepository cardRepository;
    private Board board;
    private Card card;

    @Before
    public

    void add_a_card_to_repository() {
        this.cardRepository = new MemoryCardRepository();
        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.card = new Card("Card1", this.board);
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));
    }

    @Test
    public void testEditCardColumn() {
        EditCardColumnUseCaseInput input = new EditCardColumnUseCaseInput();
        EditCardColumnUseCaseOutput output = new EditCardColumnUseCaseOutput();

        input.setCardID(this.card.getID().toString());
        input.setColumnID(this.board.get(0).getID().toString());

        EditCardColumnUseCase editCardColumnUsecase = new EditCardColumnUseCase(this.cardRepository);
        editCardColumnUsecase.execute(input, output);
        assertEquals(this.board.get(0).getID().toString(), output.getColumnID());

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(this.card.getID().toString()));
        assertEquals(this.board.get(0).getID(), card.getColumnID());
    }

}
