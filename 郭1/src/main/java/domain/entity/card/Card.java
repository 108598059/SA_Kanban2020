package domain.entity.card;

import domain.entity.Aggregate;
import domain.entity.card.event.CardCreated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Card extends Aggregate {
    private String _id ;
    private String _name ;
    private Map<String, Task> taskMap;

    public Card(){
        this._id = UUID.randomUUID().toString();
        this.taskMap = new HashMap<String, Task>();
    }

    public Card(String workflowId, String stageId, String swimlaneId){
        this._id = UUID.randomUUID().toString();
        this.taskMap = new HashMap<String, Task>();
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

    public Map<String, Task> getTaskMap(){
        return taskMap;
    }

    public String createTask(String taskName) {
        Task newTask = new Task();
        newTask.setName(taskName);
        taskMap.put(newTask.getId(), newTask);
        System.out.println("size:"+taskMap.size());
        return newTask.getId();
    }

    public void addTask(Task task){
        this.taskMap.put(task.getId(),task);
    }
}
