package domain.usecase.board;

import java.util.ArrayList;
import java.util.List;

public class BoardDTO {
    private String id;
    private String name;
    private List<String> workflows;

    public BoardDTO(){
        this.workflows = new ArrayList<String>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<String> workflows) {
        this.workflows = new ArrayList<String>(workflows);
    }
}
