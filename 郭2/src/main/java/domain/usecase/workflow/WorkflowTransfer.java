package domain.usecase.workflow;

import domain.model.aggregate.workflow.Workflow;

public class WorkflowTransfer {
    public static WorkflowDTO WorkflowToWorkflowDTO(Workflow workflow) {
        WorkflowDTO workflowDTO = new WorkflowDTO();

        workflowDTO.setBoardId(workflow.getBoardId());
        workflowDTO.setLaneList(workflow.getLaneList());
        workflowDTO.setWorkflowId(workflow.getWorkflowId());
        workflowDTO.setWorkflowName(workflow.getWorkflowName());

        return workflowDTO;
    }

    public static Workflow WorkflowDTOToWorkflow(WorkflowDTO workflowDTO) {
        Workflow workflow = new Workflow(workflowDTO.getWorkflowName());

        workflow.setWorkflowId(workflowDTO.getWorkflowId());
        workflow.setLaneList(workflowDTO.getLaneList());
        workflow.setBoardId(workflowDTO.getBoardId());

        return workflow;
    }
}
