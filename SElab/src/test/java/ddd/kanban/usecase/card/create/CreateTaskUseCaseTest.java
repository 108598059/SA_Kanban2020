package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.CardType;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.repository.CardRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CreateTaskUseCaseTest {
    private CardRepository inMemoryCardRepository;
    private String cardId;

    @Before
    public void setUp(){
        inMemoryCardRepository = new InMemoryCardRepository();
        cardId = UUID.randomUUID().toString();

        String cardName = "TestCard";
        String cardDescription = "CardDescription";
        CardType cardCardType = new CardType();
        List<String> cardTags = new ArrayList<String>(); cardTags.add("cardTag1"); cardTags.add("cardTag2");
        List<String> cardAssignUsers = new ArrayList<String>(); cardAssignUsers.add("108598034"); cardAssignUsers.add("108598087");
        Date cardPlannedStartDate = new Date();
        Date cardPlannedFinishDate = new Date();
        int cardPriority = 1;

        Card card = new Card(cardId, cardName, cardDescription, cardCardType, cardTags, cardAssignUsers, cardPlannedStartDate, cardPlannedFinishDate, cardPriority);

        inMemoryCardRepository.add(card);
    }

    @Test
    public void testCreateTask(){
        String taskTitle = "TestTask";

        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(inMemoryCardRepository);
        CreateTaskInput createTaskInput = new CreateTaskInput(taskTitle, cardId);
        CreateTaskOutput createTaskOutput = new CreateTaskOutput();

        createTaskUseCase.execute(createTaskInput, createTaskOutput);

        Card card = inMemoryCardRepository.findById(createTaskOutput.getCardId());
        Task task = card.findTaskById(createTaskOutput.getTaskId());

        assertEquals(task.getTitle(), createTaskOutput.getTaskTitle());
        assertEquals(task.getId(), createTaskOutput.getTaskId());
    }

}
