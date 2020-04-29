package domain.usecase;

import domain.adapter.CardRepositoryImpl;
import domain.controller.CreateCardInputImpl;
import domain.controller.CreateCardOutputImpl;
import domain.controller.CreateTaskInputImpl;
import domain.controller.CreateTaskOutputImpl;
import domain.entity.DomainEventBus;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.task.create.CreateTaskInput;
import domain.usecase.task.create.CreateTaskOutput;
import domain.usecase.task.create.CreateTaskUseCase;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertNotNull;

public class CreateTaskTest {
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
    public void CreateTask(){
        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(cardRepository);
        CreateTaskInput createTaskInput = new CreateTaskInputImpl();
        CreateTaskOutput createTaskOutput = new CreateTaskOutputImpl();
        createTaskInput.setCardId(cardId);
        createTaskInput.setTaskName("show_club_list");
        createTaskUseCase.execute(createTaskInput, createTaskOutput);
        assertNotNull(createTaskOutput.getTaskId());
    }

}
