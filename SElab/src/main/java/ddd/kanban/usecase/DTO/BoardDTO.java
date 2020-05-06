package ddd.kanban.usecase.DTO;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
    private final String id;
    private String title;
    private String description;
    private List<String> workflowIds;

    public BoardDTO(String id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
        this.workflowIds = new ArrayList<>();
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

    public List<String> getWorkflowIds() {
        return workflowIds;
    }

    public void setWorkflowIds(List<String> workflowIds) {
        this.workflowIds = workflowIds;
    }
}
