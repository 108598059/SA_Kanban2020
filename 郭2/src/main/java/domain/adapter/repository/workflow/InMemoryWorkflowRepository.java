package domain.adapter.repository.workflow;

import domain.usecase.workflow.WorkflowDTO;
import domain.usecase.workflow.repository.IWorkflowRepository;

import java.util.*;

public class InMemoryWorkflowRepository implements IWorkflowRepository {
    private List<WorkflowDTO> workflowList = new ArrayList<>();

    public void add(WorkflowDTO workflow) {
        workflowList.add(workflow);
    }

    public WorkflowDTO getWorkflowById(String workflowId){
        for (WorkflowDTO each:workflowList) {
            if(workflowId.equals(each.getWorkflowId()))
                return each;
        }
        throw new RuntimeException("not found workflowId = " + workflowId);
    }

    public void save(WorkflowDTO workflow) {
        for (WorkflowDTO each : workflowList) {
            if (each.getWorkflowId().equals(workflow.getWorkflowId())) {
                workflowList.set(workflowList.indexOf(each), workflow);
                break;
            }
        }
    }
}
