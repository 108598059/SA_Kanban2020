package domain.usecase.workflow;

import java.util.HashMap;
import java.util.Map;

public class WorkflowDTO {
    private String name;
    private String id;
    private Map<String, StageDTO> stages;


    public WorkflowDTO(String id){
        this.stages = new HashMap<String, StageDTO>();
        this.id = id;
    }

    public WorkflowDTO(String id, String name, Map<String, StageDTO> stages){
        this.stages = stages;
        this.id = id;
        this.name = name ;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void addStage(StageDTO stageDTO){
        this.stages.put(stageDTO.getId(),stageDTO);
    }

    public void addCard(String stageId, String swimlaneId, String cardId){
        for (StageDTO stageDTO: stages.values()){
            if(stageDTO.getId().equals(stageId)){
                for (SwimlaneDTO swimlaneDTO: stageDTO.getSwimlanes().values()){
                    if (swimlaneDTO.getId().equals(swimlaneId)) {
                        swimlaneDTO.addCard(cardId);
                    }
                }
            }
        }
    }

    /*
    public String createStage(String stageName) {
        StageDTO stageDTO = new StageDTO();
        stageDTO.setName(stageName);
        this.addStage(stageDTO);
        return stageDTO.getId();
    }

    public String createSwimlane(String stageId, String swimlaneName){
        SwimlaneDTO swimlaneDTO = new SwimlaneDTO();
        swimlaneDTO.setName(swimlaneName);

        StageDTO stageDTO = stages.get(stageId);
        stageDTO.addSwimlane(swimlaneDTO);
        return swimlaneDTO.getId();
    }

    public void moveCard(String fromStageId, String toStageId, String fromSwimLaneId, String toSwimLaneId, String cardId){
        stages.get(fromStageId).getSwimlaneById(fromSwimLaneId).getCards().remove(cardId);
        stages.get(toStageId).getSwimlaneById(toSwimLaneId).getCards().add(cardId);

    }
    */

    public String getName() {
        return this.name;
    }

    public StageDTO getStageById(String id){
        return stages.get(id);
    }

    public Map<String,StageDTO> getStages(){
        return this.stages;
    }


}
