package domain.usecase.card;

import domain.adapters.repository.CardRepositoryImpl;
import domain.adapters.controller.card.CreateCardInputImpl;
import domain.adapters.controller.card.CreateCardOutputImpl;
import domain.adapters.controller.card.CreateSubtaskInputImpl;
import domain.adapters.controller.card.CreateSubtaskOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.card.Card;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.subtask.create.CreateSubtaskInput;
import domain.usecase.subtask.create.CreateSubtaskOutput;
import domain.usecase.subtask.create.CreateSubtaskUseCase;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateSubtaskTest {
    private CardRepository cardRepository;
    private String cardId;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();

        this.cardRepository = new CardRepositoryImpl();

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);
        CreateCardInput createCardInput = new CreateCardInputImpl();
        CreateCardOutput createCardOutput = new CreateCardOutputImpl();

        createCardInput.setCardName("select_school_club");
        createCardUseCase.execute(createCardInput, createCardOutput);

        this.cardId = createCardOutput.getCardId();
    }

    @Test
    public void CreateSubtaskTest(){
        CreateSubtaskUseCase createSubtaskUseCase = new CreateSubtaskUseCase(cardRepository, eventBus);
        CreateSubtaskInput createSubtaskInput = new CreateSubtaskInputImpl();
        CreateSubtaskOutput createSubtaskOutput = new CreateSubtaskOutputImpl();

        createSubtaskInput.setCardId(cardId);
        createSubtaskInput.setSubtaskName("show_club_list");

        createSubtaskUseCase.execute(createSubtaskInput, createSubtaskOutput);

        Card card = cardRepository.getCardById(cardId);

        assertEquals(1, card.getSubtasks().size());
        assertEquals("show_club_list", card.getSubtaskById(createSubtaskOutput.getSubtaskId()).getName());
    }

}
