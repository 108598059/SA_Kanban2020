package domain.entity.card;

import domain.entity.Aggregate;
import domain.entity.card.event.CardCreated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Card extends Aggregate {
    private String _id ;
    private String _name ;
    private Map<String, Subtask> taskMap;

    public Card(){
        this._id = UUID.randomUUID().toString();
        this.taskMap = new HashMap<String, Subtask>();
    }

    public Card(String workflowId, String stageId, String swimlaneId){
        this._id = UUID.randomUUID().toString();
        this.taskMap = new HashMap<String, Subtask>();
        addEvent(new CardCreated(workflowId,stageId,swimlaneId,this._id));
    }
    public void setName( String name) {
        this._name = name ;
    }

    public String getName() {
        return this._name ;
    }

    public void setId(String id) {
        this._id = id;
    }

    public String getId() {
        return this._id ;
    }

    public Map<String, Subtask> getSubtasks(){
        return taskMap;
    }

    public Subtask getSubtaskById(String id) {
        return taskMap.get(id);
    }

    public String createSubtask(String taskName) {
        Subtask newSubtask = new Subtask();
        newSubtask.setName(taskName);
        taskMap.put(newSubtask.getId(), newSubtask);
        System.out.println("size:"+taskMap.size());
        return newSubtask.getId();
    }

    public void addSubtask(Subtask subtask){
        this.taskMap.put(subtask.getId(), subtask);
    }
}
