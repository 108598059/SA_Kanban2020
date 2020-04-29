package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateTaskInput {
    private String cardId;
    private String taskTitle;
    private String taskDescription;
    private CardType taskTaskType;
    private List<String> taskTags;
    private List<String> taskAssignUsers;
    private Date taskPlannedStartDate;
    private Date taskPlannedFinishDate;
    private String taskHeader;
    private int taskPriority;
    private String taskExternalLink;

    public CreateTaskInput(String cardId, String taskTitle, String taskDescription, CardType taskTaskType, List<String> taskTags, List<String> taskAssignUsers, Date taskPlannedStartDate, Date taskPlannedFinishDate, String taskHeader, int taskPriority, String taskExternalLink){
        this.cardId = cardId;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskTaskType = taskTaskType;
        this.taskTags = taskTags;
        this.taskAssignUsers = taskAssignUsers;
        this.taskPlannedStartDate = taskPlannedStartDate;
        this.taskPlannedFinishDate = taskPlannedFinishDate;
        this.taskHeader = taskHeader;
        this.taskPriority = taskPriority;
        this.taskExternalLink = taskExternalLink;
    }

    public String getCardId() {
        return cardId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public CardType getTaskTaskType() {
        return taskTaskType;
    }

    public List<String> getTaskTags() {
        return taskTags;
    }

    public List<String> getTaskAssignUsers() {
        return taskAssignUsers;
    }

    public Date getTaskPlannedStartDate() {
        return taskPlannedStartDate;
    }

    public Date getTaskPlannedFinishDate() {
        return taskPlannedFinishDate;
    }

    public String getTaskHeader() {
        return taskHeader;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public String getTaskExternalLink() {
        return taskExternalLink;
    }

}
