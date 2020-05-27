package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.column.EditCardBelongsColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class EditCardColumnUseCaseTest {
    private CardRepository cardRepository;
    private Board board;
    private Card card;
    private DomainEventBus eventBus;

    @Before
    public void add_a_card_to_card_repository() {
        this.cardRepository = new MemoryCardRepository();
        this.board = new Board(UUID.randomUUID(), "Kanban");
        this.card = new Card("Edit card column", this.board);
        this.cardRepository.save(CardDTOConverter.toDTO(this.card));
        this.eventBus = new DomainEventBus();
    }

    @Test
    public void edit_card_column_should_update_column_id_of_card() {
        EditCardBelongsColumnUseCase editCardBelongsColumnUsecase = new EditCardBelongsColumnUseCase(this.eventBus, this.cardRepository);
        EditCardBelongsColumnUseCaseInput input = new EditCardBelongsColumnUseCaseInput();
        EditCardBelongsColumnUseCaseOutput output = new EditCardBelongsColumnUseCaseOutput();

        input.setCardID(this.card.getID().toString());
        input.setColumnID(this.board.getBacklogColumn().getID().toString());

        editCardBelongsColumnUsecase.execute(input, output);

        assertEquals(this.board.getBacklogColumn().getID().toString(), output.getColumnID());

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(this.card.getID().toString()));
        assertEquals(this.board.getBacklogColumn().getID(), card.getBelongsColumnID());
    }

}
