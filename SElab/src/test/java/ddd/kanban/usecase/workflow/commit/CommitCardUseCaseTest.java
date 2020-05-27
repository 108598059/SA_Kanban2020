package ddd.kanban.usecase.workflow.commit;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.workflow.Lane;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.FlowEventRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private WorkflowRepository workflowRepository;
    private HierarchyInitial hierarchyInitial;
    private BoardRepository boardRepository;
    private FlowEventRepository flowEventRepository;
    private DomainEventBus domainEventBus;
    private String columnId;
    private String workflowId;
    private String boardId;

    @Before
    public void setUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        this.domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, this.domainEventBus));
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        this.boardId = hierarchyInitial.CreateBoard();
        this.workflowId = hierarchyInitial.CreateWorkflow(this.boardId);
        this.columnId = hierarchyInitial.CreateColumn(this.workflowId);
    }

    @Test
    public void testCommitCard(){
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(this.workflowId));
        Lane lane = workflow.findColumnById(this.columnId);

        assertEquals(0, lane.getCommittedCards().size());

        Card card = new Card(UUID.randomUUID().toString(), "Card", this.boardId, this.workflowId, this.columnId);
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardInput commitCardInput = new CommitCardInput(card.getId(), this.workflowId, this.columnId);
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);

        assertEquals(1, lane.getCommittedCards().size());
    }
}
