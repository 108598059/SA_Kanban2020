package ddd.kanban.domain.model.workflow;

public class Column {

    private String title;
    private String id;
    private String workflowId;

    public Column(String title, String id, String workflowId){
        this.workflowId = workflowId;
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }
}
