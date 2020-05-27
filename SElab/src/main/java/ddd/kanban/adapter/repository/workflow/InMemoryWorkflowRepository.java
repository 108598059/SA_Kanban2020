package ddd.kanban.adapter.repository.workflow;

import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.entity.WorkflowEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InMemoryWorkflowRepository implements WorkflowRepository {
    private List<WorkflowEntity> workflowEntities;

    public InMemoryWorkflowRepository(){
        workflowEntities = new ArrayList<WorkflowEntity>();
    }

    @Override
    public void add(WorkflowEntity workflow){
        workflowEntities.add(workflow);
    }

    @Override
    public WorkflowEntity findById(String workflowId){
        return workflowEntities.stream()
                .filter(findWorkflowById(workflowId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void save(WorkflowEntity workflowEntity){
        for(WorkflowEntity each : workflowEntities){
            if (each.getId().equalsIgnoreCase(workflowEntity.getId()))
                workflowEntities.set(workflowEntities.indexOf(each), workflowEntity);
        }
    }

    @Override
    public List<WorkflowEntity> findAll() {
        return workflowEntities;
    }

    private static Predicate<WorkflowEntity> findWorkflowById(String workflowId){
        return workflow -> workflow.getId().equals(workflowId);
    }
}
