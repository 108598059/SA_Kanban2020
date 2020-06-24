package domain.entity.aggregate.card;

import domain.entity.aggregate.Aggregate;
import domain.entity.aggregate.card.event.CardCreated;
import domain.entity.aggregate.card.event.SubtaskCreated;

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
    public Card(String id, String name, Map<String, Subtask> taskMap){
        this._id = id;
        this._name = name;
        this.taskMap = taskMap;
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

        SubtaskCreated subtaskCreated = new SubtaskCreated(this._id, newSubtask.getId(), newSubtask.getName()) ;
        addEvent(subtaskCreated);

        return newSubtask.getId();
    }

    public void addSubtask(Subtask subtask){
        this.taskMap.put(subtask.getId(), subtask);
    }
}
