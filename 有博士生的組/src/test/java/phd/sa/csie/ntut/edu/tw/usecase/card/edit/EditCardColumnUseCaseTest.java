package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditCardColumnUseCaseTest {
    private CardRepository cardRepository;
    private Board board;
    private Card card;

    @Before
    public void add_a_card_to_card_repository() {
        this.cardRepository = new MemoryCardRepository();
        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.card = new Card("Edit card column", this.board);
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));
    }

    @Test
    public void edit_card_column_should_update_column_id_of_card() {
        EditCardColumnUseCase editCardColumnUsecase = new EditCardColumnUseCase(this.cardRepository);
        EditCardColumnUseCaseInput input = new EditCardColumnUseCaseInput();
        EditCardColumnUseCaseOutput output = new EditCardColumnUseCaseOutput();

        input.setCardID(this.card.getID().toString());
        input.setColumnID(this.board.getBacklogColumn().getID().toString());

        editCardColumnUsecase.execute(input, output);

        assertEquals(this.board.getBacklogColumn().getID().toString(), output.getColumnID());

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(this.card.getID().toString()));
        assertEquals(this.board.getBacklogColumn().getID(), card.getColumnID());
    }

}
