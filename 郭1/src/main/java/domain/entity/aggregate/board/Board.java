package domain.entity.aggregate.board;

import domain.entity.aggregate.Aggregate;
import domain.entity.aggregate.board.event.BoardCreated;
import domain.entity.aggregate.board.event.WorkflowCommitted;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Board extends Aggregate {
    private String id;
    private String name;
    private List<String> workflows;

    public Board(String commitToTeamId){
        workflows = new ArrayList<String>();
        this.id = UUID.randomUUID().toString();
        BoardCreated boardCreated = new BoardCreated( this.id, this.name, commitToTeamId ) ;
        addEvent(boardCreated);
    }

    public Board(String id, String name, List<String> workflows) {
        this.id = id;
        this.name = name;
        this.workflows = workflows;
    }

    public void add(String workflowId){
        workflows.add(workflowId);
        WorkflowCommitted workflowCommitted = new WorkflowCommitted( this.id , workflowId ) ;
        addEvent(workflowCommitted);
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

    public String getWorkflowById(String workflowId) {
        for(String workflow: workflows){
            if(workflow.equals(workflowId))
                return workflow;
        }
        return "";
    }

    public List<String> getWorkflows(){
        return workflows;
    }

    public int getNumberOfWorkflow() {
        return workflows.size();
    }
}
