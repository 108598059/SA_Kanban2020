package ddd.kanban.usecase.kanbanboard.workflow.create;

public class CreateColumnOutput {

    private String columnId;
    private String columnTitle;

    public CreateColumnOutput(){

    }

    public String getColumnTitle() {
        return columnTitle;
    }

    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnId(){
        return this.columnId;
    }
}
