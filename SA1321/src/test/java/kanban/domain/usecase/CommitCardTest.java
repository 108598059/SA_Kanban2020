package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.card.commit.CommitCardInput;
import kanban.domain.usecase.card.commit.CommitCardOutput;
import kanban.domain.usecase.card.commit.CommitCardUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommitCardTest {
    private String boardId;
    private String workflowId;
    private String stageId;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;
    private Utility utility;

    @Before
    public void setup() {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
        boardId = utility.createBoard("test automation");
        workflowId = utility.createWorkflow(boardId,"workflowName");
        stageId = utility.createStage(workflowId,"stageName");
    }

    @Test
    public void Should_Card_Can_Commit_In_Stage() {
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput input = new CommitCardInput();
        input.setCardId("cardId");
        input.setWorkflowId(workflowId);
        input.setStageId(stageId);
        CommitCardOutput output = new CommitCardOutput();

        commitCardUseCase.execute(input, output);

        assertEquals("cardId", output.getCardId());
    }
}
