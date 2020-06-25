package domain.usecase.card;

import domain.entity.aggregate.card.Subtask;
import domain.entity.aggregate.card.event.CardCreated;
import domain.entity.aggregate.card.event.SubtaskCreated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CardDTO {
    private String _id ;
    private String _name ;
    private Map<String, SubtaskDTO> taskMap;


    public CardDTO(String id, String name, Map<String, Subtask> taskMap){
        this._id = id;
        this._name = name;
        this.taskMap = new HashMap<String, SubtaskDTO>();
        for (Map.Entry<String, Subtask> entry : taskMap.entrySet()) {
            this.taskMap.put(entry.getKey(),new SubtaskDTO(entry.getValue().getId(),entry.getValue().getName())) ;
        }

    }


    public CardDTO(String id, String name){
        this._id = id;
        this._name = name;
        this.taskMap = new HashMap<String, SubtaskDTO>();
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

    public Map<String, SubtaskDTO> getSubtasks(){
        return taskMap;
    }



    public void addSubtask(SubtaskDTO subtaskDTO){
        this.taskMap.put(subtaskDTO.getId(), subtaskDTO);
    }
}
