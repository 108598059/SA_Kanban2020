package domain.usecase.workflow;

import domain.entity.aggregate.workflow.Stage;
import domain.entity.aggregate.workflow.Swimlane;
import domain.entity.aggregate.workflow.Workflow;

import java.util.HashMap;
import java.util.Map;

public class WorkflowTransformer {
    public static WorkflowDTO toDTO(Workflow workflow){

        Map<String, StageDTO> stageDTOMap = new HashMap<String, StageDTO>() ;
        for (Map.Entry<String, Stage> stageEntry : workflow.getStages().entrySet() ) {

            Map<String , SwimlaneDTO> swimlaneDTOMap = new HashMap<String, SwimlaneDTO>() ;
            for ( Map.Entry<String, Swimlane> swimlaneEntry: stageEntry.getValue().getSwimlanes().entrySet() ) {
                SwimlaneDTO swimlaneDTO = new SwimlaneDTO( swimlaneEntry.getValue().getId(), swimlaneEntry.getValue().getName(),swimlaneEntry.getValue().getCards() ) ;
                swimlaneDTOMap.put(swimlaneEntry.getKey(),swimlaneDTO) ;
            }

            StageDTO stageDTO = new StageDTO(stageEntry.getValue().getId(),stageEntry.getValue().getName(),swimlaneDTOMap) ;
            stageDTOMap.put(stageEntry.getKey(),stageDTO);
        }

        WorkflowDTO workflowDTO = new WorkflowDTO(workflow.getId(),workflow.getName(),stageDTOMap) ;

        return workflowDTO;
    }

    public static Workflow toWorkflow(WorkflowDTO workflowDTO){
        Map<String, Stage> stageMap = new HashMap<String, Stage>() ;
        for (Map.Entry<String, StageDTO> stageEntry : workflowDTO.getStages().entrySet() ) {

            Map<String, Swimlane> swimlaneMap = new HashMap<String, Swimlane>() ;
            for ( Map.Entry<String, SwimlaneDTO> swimlaneEntry: stageEntry.getValue().getSwimlanes().entrySet() ) {
                Swimlane swimlane = new Swimlane( swimlaneEntry.getValue().getId(), swimlaneEntry.getValue().getName(),swimlaneEntry.getValue().getCards() ) ;
                swimlaneMap.put(swimlaneEntry.getKey(),swimlane) ;
            }

            Stage stage = new Stage(stageEntry.getValue().getId(),stageEntry.getValue().getName(),swimlaneMap) ;
            stageMap.put(stageEntry.getKey(),stage);
        }

        Workflow workflow = new Workflow(workflowDTO.getId(),workflowDTO.getName(),stageMap) ;
        return workflow;


    }
}
