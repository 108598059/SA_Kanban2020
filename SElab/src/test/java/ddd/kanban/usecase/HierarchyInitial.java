package ddd.kanban.usecase;

import ddd.kanban.adapter.presenter.kanbanboard.board.create.CreateBoardPresenter;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardInput;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardOutput;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.create.*;

public class HierarchyInitial {
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus domainEventBus;
    public HierarchyInitial(BoardRepository boardRepository, WorkflowRepository workflowRepository, DomainEventBus domainEventBus){
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.domainEventBus = domainEventBus;
    }


    public String CreateBoard(){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, domainEventBus);
        CreateBoardInput createBoardInput = new CreateBoardInput("Board1", "New Board");
        CreateBoardOutput createBoardOutput = new CreateBoardPresenter();

        createBoardUseCase.execute(createBoardInput, createBoardOutput);

        return createBoardOutput.getBoardId();
    }

    public String CreateWorkflow(String boardId){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, domainEventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput( "Workflow1", boardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        return createWorkflowOutput.getWorkflowId();
    }

    public String CreateColumn(String workflowId){
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(workflowRepository, domainEventBus);
        CreateColumnInput createColumnInput = new CreateColumnInput("column1", workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        return createColumnOutput.getColumnId();
    }
}
