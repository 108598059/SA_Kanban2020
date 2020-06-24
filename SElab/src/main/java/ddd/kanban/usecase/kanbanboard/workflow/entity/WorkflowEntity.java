package ddd.kanban.usecase.kanbanboard.workflow.entity;

import java.util.List;

public class WorkflowEntity {
    private String id;
    private String title;
    private String boardId;
    private List<ColumnEntity> columnEntities;

    public WorkflowEntity(String id, String title, String boardId) {
        this.id = id;
        this.title = title;
        this.boardId = boardId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBoardId() {
        return boardId;
    }

    public List<ColumnEntity> getColumnEntities() {
        return columnEntities;
    }

    public void setColumnEntities(List<ColumnEntity> columnEntities) {
        this.columnEntities = columnEntities;
    }
}
