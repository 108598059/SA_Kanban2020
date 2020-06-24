package domain.usecase.workflow;

import domain.entity.aggregate.team.Team;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.team.TeamDTO;

public class WorkflowTransformer {
    public static WorkflowDTO toDTO(Workflow workflow){
        WorkflowDTO workflowDTO = new WorkflowDTO(workflow);

        return workflowDTO;
    }

    public static Workflow toWorkflow(WorkflowDTO workflowDTO){
        return new Workflow(workflowDTO.getId(), workflowDTO.getName(), workflowDTO.getStages());

    }
}
