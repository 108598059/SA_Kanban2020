package domain.usecase;

import domain.adapters.repository.BoardRepositoryImpl;
import domain.adapters.repository.WorkflowRepositoryImpl;
import domain.adapters.controller.board.CreateBoardInputImpl;
import domain.adapters.controller.board.CreateBoardOutputImpl;
import domain.adapters.controller.workflow.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.workflow.WorkflowEventHandler;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkFlowTest {

    private String boardId;
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        boardRepository = new BoardRepositoryImpl();


        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("board1");


        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        createBoardUseCase.execute(createBoardInput,createBoardOutput);


        boardId = createBoardOutput.getBoardId();


        workflowRepository = new WorkflowRepositoryImpl();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository));
    }

    @Test
    public void Create_workflow_should_commit_workflow_in_its_board(){
        WorkflowRepository workflowRepository = new WorkflowRepositoryImpl();


        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();

        createWorkflowInput.setWorkflowName("workflow1");
        createWorkflowInput.setBoardId(boardId);

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        assertNotNull(createWorkflowOutput.getWorkflowId());


        assertEquals(1,boardRepository.getBoardById(boardId).getWorkflows().size());

    }



}
