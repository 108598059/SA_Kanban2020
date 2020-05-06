package domain.usecase;

import domain.adapter.CardRepositoryImpl;
import domain.controller.CreateCardInputImpl;
import domain.controller.CreateCardOutputImpl;
import domain.controller.CreateSubtaskInputImpl;
import domain.controller.CreateSubtaskOutputImpl;
import domain.entity.DomainEventBus;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.subtask.create.CreateSubtaskInput;
import domain.usecase.subtask.create.CreateSubtaskOutput;
import domain.usecase.subtask.create.CreateSubtaskUseCase;
import org.junit.Test;
import org.junit.Before;

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
    public void CreateSubtask(){
        CreateSubtaskUseCase createSubtaskUseCase = new CreateSubtaskUseCase(cardRepository);
        CreateSubtaskInput createSubtaskInput = new CreateSubtaskInputImpl();
        CreateSubtaskOutput createSubtaskOutput = new CreateSubtaskOutputImpl();

        createSubtaskInput.setCardId(cardId);
        createSubtaskInput.setSubtaskName("show_club_list");

        createSubtaskUseCase.execute(createSubtaskInput, createSubtaskOutput);

        assertNotNull(createSubtaskOutput.getSubtaskId());
    }

}
