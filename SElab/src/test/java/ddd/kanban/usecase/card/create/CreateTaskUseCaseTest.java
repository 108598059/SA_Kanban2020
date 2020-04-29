package ddd.kanban.usecase.card.create;

import ddd.kanban.adapter.repository.card.InMemoryCardRepository;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.CardType;
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
        String taskDescription = "taskDescription";
        CardType taskTaskType = new CardType();
        List<String> taskTags = new ArrayList<String>(); taskTags.add("taskTag1"); taskTags.add("taskTag2");
        List<String> taskAssignUsers = new ArrayList<String>(); taskAssignUsers.add("108598034"); taskAssignUsers.add("108598087");
        Date taskPlannedStartDate = new Date();
        Date taskPlannedFinishDate = new Date();
        String taskHeader = "taskHeader";
        int taskPriority = 1;
        String taskExternalLink = "www.google.com";

        CreateTaskInput createTaskInput = new CreateTaskInput(cardId, taskTitle, taskDescription, taskTaskType, taskTags, taskAssignUsers, taskPlannedStartDate, taskPlannedFinishDate, taskHeader, taskPriority, taskExternalLink);
        CreateTaskOutput createTaskOutput = new CreateTaskOutput();
        CreateTaskUseCase createTaskUseCase = new CreateTaskUseCase(inMemoryCardRepository);

        createTaskUseCase.execute(createTaskInput, createTaskOutput);

        assertEquals(taskTitle, createTaskOutput.getTaskTitle());
        assertEquals(taskDescription, createTaskOutput.getTaskDescription());
        assertEquals(taskTaskType, createTaskOutput.getTaskTaskType());
        assertEquals(taskTags, createTaskOutput.getTaskTags());
        assertEquals(taskAssignUsers, createTaskOutput.getTaskAssignUsers());
        assertEquals(taskPlannedStartDate, createTaskOutput.getTaskPlannedStartDate());
        assertEquals(taskPlannedFinishDate, createTaskOutput.getTaskPlannedFinishDate());
        assertEquals(taskHeader, createTaskOutput.getTaskHeader());
        assertEquals(taskPriority, createTaskOutput.getTaskPriority());
        assertEquals(taskExternalLink, createTaskOutput.getTaskExternalLink());
    }

}
