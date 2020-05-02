package ddd.kanban.usecase.board.create;


import ddd.kanban.domain.model.board.Board;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.create.CreateWorkflowInput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowOutput;
import ddd.kanban.usecase.workflow.create.CreateWorkflowUseCase;

import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;

    public CreateBoardUseCase(BoardRepository boardRepository, WorkflowRepository workflowRepository){
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
    }


    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {
        Board board = new Board(UUID.randomUUID().toString(), createBoardInput.getBoardName(), createBoardInput.getBoardDescription());
        boardRepository.add(board);
        boardRepository.save();

        createDefaultWorkflow(board.getId());

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardName(board.getName());
        createBoardOutput.setBoardDescription(board.getDescription());
    }

    private void createDefaultWorkflow(String boardId){
        final String defaultWorkflowTitle = "Default Workflow";
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(this.workflowRepository);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput(defaultWorkflowTitle, boardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

    }
}
