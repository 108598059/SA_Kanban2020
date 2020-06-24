package ddd.kanban.usecase.card.entity;

import java.util.Date;
import java.util.List;

public class TaskEntity {

    private String id;
    private String title;
    private String description;
    private List<String> tags;
    private List<String> assignUsers;
    private Date plannedStartDate;
    private Date plannedFinishDate;
    private String header;
    private int priority;
    private String externalLink;

    public TaskEntity(String id, String title, String description, List<String> tags, List<String> assignUsers, Date plannedStartDate, Date plannedFinishDate, String header, int priority, String externalLink){
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.assignUsers = assignUsers;
        this.plannedStartDate = plannedStartDate;
        this.plannedFinishDate = plannedFinishDate;
        this.header = header;
        this.priority = priority;
        this.externalLink = externalLink;
    }


    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getAssignUsers() {
        return assignUsers;
    }

    public Date getPlannedStartDate() {
        return plannedStartDate;
    }

    public Date getPlannedFinishDate() {
        return plannedFinishDate;
    }

    public String getHeader() {
        return header;
    }

    public int getPriority() {
        return priority;
    }

    public String getExternalLink() {
        return externalLink;
    }
}
