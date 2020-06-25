package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.presenter.card.create.CreateCardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.card.MemoryCardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.model.aggregate.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.board.commit.card.CommitCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.name.EditCardNameUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.name.EditCardNameUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.edit.name.EditCardNameUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.card.CardRepository;

public class EditCardNameUseCaseTest {

    private Card card;
    private CardRepository cardRepository;
    private DomainEventBus eventBus;

    @Before
    public void given_a_card() {
        this.eventBus = new DomainEventBus();
        this.cardRepository = new MemoryCardRepository();
        BoardRepository boardRepository = new MemoryBoardRepository();

        Board board = new Board(UUID.randomUUID(), "Kanban");
        boardRepository.save(BoardDTOConverter.toDTO(board));

        this.eventBus.register(new CommitCardUseCase(this.eventBus, this.cardRepository, boardRepository));
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(this.eventBus, this.cardRepository);
        CreateCardUseCaseInput createCardUseCaseInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardUseCaseOutput = new CreateCardPresenter();

        createCardUseCaseInput.setCardName("Old Name");
        createCardUseCaseInput.setBoardID(board.getID().toString());

        createCardUseCase.execute(createCardUseCaseInput, createCardUseCaseOutput);

        this.card = CardDTOConverter.toEntity(this.cardRepository.findByID(createCardUseCaseOutput.getCardID()));
    }

    @Test
    public void edit_card_name_should_update_the_name_of_card() {
        EditCardNameUseCase editCardNameUseCase = new EditCardNameUseCase(this.eventBus, this.cardRepository);
        EditCardNameUseCaseInput editCardNameUseCaseInput = new EditCardNameUseCaseInput();
        EditCardNameUseCaseOutput editCardNameUseCaseOutput = new EditCardNameUseCaseOutput();

        editCardNameUseCaseInput.setCardID(this.card.getID().toString());
        editCardNameUseCaseInput.setCardName("New Name");

        editCardNameUseCase.execute(editCardNameUseCaseInput, editCardNameUseCaseOutput);

        assertEquals(this.card.getID().toString(), editCardNameUseCaseOutput.getCardID());
        assertEquals("New Name", editCardNameUseCaseOutput.getCardName());

        Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(this.card.getID().toString()));
        assertEquals(editCardNameUseCaseInput.getCardName(), card.getName());
    }

    @Test
    public void edit_card_name_is_empty_should_raise_illegal_argument_exception() {
        EditCardNameUseCase editCardNameUseCase = new EditCardNameUseCase(this.eventBus, this.cardRepository);
        EditCardNameUseCaseInput editCardNameUseCaseInput = new EditCardNameUseCaseInput();
        EditCardNameUseCaseOutput editCardNameUseCaseOutput = new EditCardNameUseCaseOutput();

        editCardNameUseCaseInput.setCardID(this.card.getID().toString());
        editCardNameUseCaseInput.setCardName("");

        try {
            editCardNameUseCase.execute(editCardNameUseCaseInput, editCardNameUseCaseOutput);
        } catch (IllegalArgumentException e) {
            assertEquals("Card name should not be empty", e.getMessage());
            return;
        }
        fail("Edit card name is empty should raise IllegalArgumentException");
    }
}