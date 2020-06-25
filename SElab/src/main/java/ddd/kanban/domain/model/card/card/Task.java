package ddd.kanban.domain.model.card.card;

import ddd.kanban.domain.model.Entity;

import java.util.Date;
import java.util.List;

public class Task extends Entity {

    private String description;
    private List<String> tags;
    private List<String> assignUsers;
    private Date plannedStartDate;
    private Date plannedFinishDate;
    private String header;
    private int priority;
    private String externalLink;

    public Task(final String id, String title){
        super(id, title);
    }

    public Task(String id, String title, String description, List<String> tags, List<String> assignUsers, Date plannedStartDate, Date plannedFinishDate, String header, int priority, String externalLink){
        super(id, title);
        this.description = description;
        this.assignUsers = assignUsers;
        this.plannedStartDate = plannedStartDate;
        this.plannedFinishDate = plannedFinishDate;
        this.header = header;
        this.priority = priority;
        this.externalLink = externalLink;
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
