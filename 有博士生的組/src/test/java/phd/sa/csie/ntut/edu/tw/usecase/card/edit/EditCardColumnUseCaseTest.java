package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import static org.junit.Assert.assertEquals;

public class EditCardColumnUseCaseTest {
    private CardRepository cardRepository;
    private Board board;
    private Card card;

    @Before
    public

    void add_a_card_to_repository() {
        this.cardRepository = new MemoryCardRepository();
        this.board = new Board("Kanban");
        this.card = new Card("Card1", this.board);
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));
    }

    @Test
    public void testEditCardColumn() {
        EditCardColumnInput input = new EditCardColumnInput();
        EditCardColumnOutput output = new EditCardColumnOutput();

        input.setCardID(this.card.getId().toString());
        input.setColumnID(this.board.get(0).getId().toString());

        EditCardColumnUsecase editCardColumnUsecase = new EditCardColumnUsecase(this.cardRepository);
        editCardColumnUsecase.execute(input, output);
        assertEquals(this.board.get(0).getId().toString(), output.getColumnID());

        Card card = CardDTOConverter.toEntity(this.cardRepository.findById(this.card.getId().toString()));
        assertEquals(this.board.get(0).getId(), card.getColumnId());
    }

}
