package domain.adapter.repository.workflow;

import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;

import java.util.*;

public class InMemoryWorkflowRepository implements IWorkflowRepository {
    private ArrayList<Workflow> workflowList = new ArrayList<Workflow>();

    public void add(Workflow workflow) {
        workflowList.add(workflow);
    }

    public Workflow getWorkflowById(String workflowId){
        for (Workflow each:workflowList) {
            if(workflowId.equals(each.getWorkflowId()))
                return each;
        }
        throw new RuntimeException("not found workflowId = " + workflowId);
    }

    public void save(Workflow workflow) {
        for (Workflow each : workflowList) {
            if (each.getWorkflowId().equals(workflow.getWorkflowId())) {
                workflowList.set(workflowList.indexOf(each), workflow);
                break;
            }
        }
    }
}
