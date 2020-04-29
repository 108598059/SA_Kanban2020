package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.repository.CardRepository;

public class CreateTaskUseCase {
    private CardRepository cardRepository;

    public CreateTaskUseCase(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    public void execute(CreateTaskInput createTaskInput, CreateTaskOutput createTaskOutput) {
        Card card = cardRepository.findById(createTaskInput.getCardId());

        //String taskId = card.createTask(createTaskInput.getTaskTitle());
        String taskId = card.createTask(new Task("",
                                            createTaskInput.getTaskTitle(),
                                            createTaskInput.getTaskDescription(),
                                            createTaskInput.getTaskTaskType(),
                                            createTaskInput.getTaskTags(),
                                            createTaskInput.getTaskAssignUsers(),
                                            createTaskInput.getTaskPlannedStartDate(),
                                            createTaskInput.getTaskPlannedFinishDate(),
                                            createTaskInput.getTaskHeader(),
                                            createTaskInput.getTaskPriority(),
                                            createTaskInput.getTaskExternalLink()
                                            )
                                        );

        cardRepository.save(card);

        Card outputCard = cardRepository.findById(card.getId());
        Task outputTask = outputCard.findTaskById(taskId);

        createTaskOutput.setTaskTitle(outputTask.getTitle());
        createTaskOutput.setTaskDescription(outputTask.getDescription());
        createTaskOutput.setTaskTaskType(outputTask.getTaskType());
        createTaskOutput.setTaskTags(outputTask.getTags());
        createTaskOutput.setTaskAssignUsers(outputTask.getAssignUsers());
        createTaskOutput.setTaskPlannedStartDate(outputTask.getPlannedStartDate());
        createTaskOutput.setTaskPlannedFinishDate(outputTask.getPlannedFinishDate());
        createTaskOutput.setTaskHeader(outputTask.getHeader());
        createTaskOutput.setTaskPriority(outputTask.getPriority());
        createTaskOutput.setTaskExternalLink(outputTask.getExternalLink());
    }
}
