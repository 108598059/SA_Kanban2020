package ddd.kanban.usecase.workflow.create;

import ddd.kanban.domain.model.workflow.Column;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.WorkflowRepository;

public class CreateWorkflowUseCase {
    private WorkflowRepository workflowRepository;
    public CreateWorkflowUseCase(WorkflowRepository workflowRepository){
        this.workflowRepository = workflowRepository;
    }

    public void execute(CreateWorkflowInput createWorkflowInput, CreateWorkflowOutput createWorkflowOutput) {
        Workflow workflow = new Workflow(createWorkflowInput.getWorkflowId(),createWorkflowInput.getWorkflowTitle(),createWorkflowInput.getWorkflowBoardId());
        createWorkflowOutput.setWorkflowId(workflow.getId());
        createWorkflowOutput.setWorkflowTitle(workflow.getTitle());
        createWorkflowOutput.setWorkflowBoardId(workflow.getBoardId());
        //create default column
//        CreateColumnInput createColumnInput = new CreateColumnInput("defalut column", workflow.getId());
//        String columnName=workflow.createColumn(createColumnInput.getColumnTitle(), createColumnInput.getWorkflowId());
//        CreateColumnOutput createColumnOutput=new CreateColumnOutput();
//        createColumnOutput.setColumnTitle(columnName);

        workflowRepository.add(workflow);
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(workflowRepository);
        CreateColumnInput createColumnInput = new CreateColumnInput("column", workflow.getId());
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();
        createColumnUseCase.execute(createColumnInput, createColumnOutput);


    }

}