package domain.entity.board;

import domain.entity.Aggregate;
import domain.entity.workflow.Workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends Aggregate {
    private String id;
    private String name;
    private List<String> workflows;

    public Board(){
        workflows = new ArrayList<String>();
        this.id = UUID.randomUUID().toString();
    }

    public void add(String workflowId){
        workflows.add(workflowId);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public Boolean isWorkflowExist(String workflowId){
        for(String id: workflows){
            if(id.equals(workflowId))
                return true;
        }
        return false;
    }

    public int getSize() {
        return workflows.size();
    }
}
