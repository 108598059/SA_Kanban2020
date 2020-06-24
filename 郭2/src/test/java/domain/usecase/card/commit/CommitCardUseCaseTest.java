package domain.usecase.card.commit;

import static org.junit.Assert.*;
import domain.adapter.repository.board.MySqlBoardRepository;
import domain.adapter.repository.workflow.MySqlWorkflowRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.board.create.CreateBoardUseCaseInput;
import domain.usecase.board.create.CreateBoardUseCaseOutputImpl;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.stage.create.CreateStageUseCase;
import domain.usecase.stage.create.CreateStageUseCaseInput;
import domain.usecase.stage.create.CreateStageUseCaseOutput;
import domain.usecase.workflow.WorkflowTransfer;
import domain.usecase.workflow.create.CreateWorkflowUseCase;
import domain.usecase.workflow.create.CreateWorkflowUseCaseInput;
import domain.usecase.workflow.create.CreateWorkflowUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

public class CommitCardUseCaseTest {
    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;

    private CreateWorkflowUseCase createWorkflowUseCase;
    private CreateWorkflowUseCaseOutput workflowOutput;
    private CreateStageUseCase createStageUseCase;
    private CreateStageUseCaseOutput stageOutput;

    private DomainEventBus eventBus;

    @Before
    public void SetUp() throws CloneNotSupportedException {
        eventBus = new DomainEventBus();
        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();

        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutputImpl createBoardUseCaseOutputImpl = new CreateBoardUseCaseOutputImpl();
        createBoardUseCaseInput.setBoardName("Kanban of KanbanDevelopment");
        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutputImpl);


        createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, eventBus);
        CreateWorkflowUseCaseInput workflowInput = new CreateWorkflowUseCaseInput();
        workflowOutput = new CreateWorkflowUseCaseOutput();
        workflowInput.setWorkflowName("KanbanDevelopment");
        workflowInput.setBoardId(createBoardUseCaseOutputImpl.getBoardId());
        createWorkflowUseCase.execute(workflowInput, workflowOutput);

        createStageUseCase = new CreateStageUseCase(workflowRepository,eventBus);
        stageOutput = new CreateStageUseCaseOutput();
        CreateStageUseCaseInput stageInput = new CreateStageUseCaseInput();
        stageInput.setStageName("ToDo");
        stageInput.setWorkflowId(workflowOutput.getWorkflowId());
        createStageUseCase.execute(stageInput, stageOutput);
    }

    @Test
    public void card_should_be_committed_in_its_Lane() throws CloneNotSupportedException {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, eventBus);
        CommitCardUseCaseOutput output = new CommitCardUseCaseOutput();
        CommitCardUseCaseInput input = new CommitCardUseCaseInput();

        input.setCardId("cardId");
        input.setWorkflowId(workflowOutput.getWorkflowId());
        input.setLaneId(stageOutput.getStageId());

        commitCardUseCase.execute(input, output);

        Workflow workflow = WorkflowTransfer.WorkflowDTOToWorkflow(workflowRepository.getWorkflowById(input.getWorkflowId()));

        assertTrue(workflow.getLaneById(stageOutput.getStageId()).getCardIdList().contains(output.getCardId()));
    }
}
