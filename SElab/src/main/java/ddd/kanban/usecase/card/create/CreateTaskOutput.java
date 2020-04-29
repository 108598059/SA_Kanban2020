package ddd.kanban.usecase.card.create;

import ddd.kanban.domain.model.card.CardType;

import java.util.Date;
import java.util.List;

public class CreateTaskOutput {

    private String taskId;
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

    public CreateTaskOutput(){

    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public CardType getTaskTaskType() {
        return taskTaskType;
    }

    public void setTaskTaskType(CardType taskTaskType) {
        this.taskTaskType = taskTaskType;
    }

    public List<String> getTaskTags() {
        return taskTags;
    }

    public void setTaskTags(List<String> taskTags) {
        this.taskTags = taskTags;
    }

    public List<String> getTaskAssignUsers() {
        return taskAssignUsers;
    }

    public void setTaskAssignUsers(List<String> taskAssignUsers) {
        this.taskAssignUsers = taskAssignUsers;
    }

    public Date getTaskPlannedStartDate() {
        return taskPlannedStartDate;
    }

    public void setTaskPlannedStartDate(Date taskPlannedStartDate) {
        this.taskPlannedStartDate = taskPlannedStartDate;
    }

    public Date getTaskPlannedFinishDate() {
        return taskPlannedFinishDate;
    }

    public void setTaskPlannedFinishDate(Date taskPlannedFinishDate) {
        this.taskPlannedFinishDate = taskPlannedFinishDate;
    }

    public String getTaskHeader() {
        return taskHeader;
    }

    public void setTaskHeader(String taskHeader) {
        this.taskHeader = taskHeader;
    }

    public int getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(int taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskExternalLink() {
        return taskExternalLink;
    }

    public void setTaskExternalLink(String taskExternalLink) {
        this.taskExternalLink = taskExternalLink;
    }

}
