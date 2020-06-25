package ddd.kanban.usecase.card.commit;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.card.card.Card;
import ddd.kanban.domain.model.kanbanboard.workflow.Column;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.domainevent.handler.DomainEventHandler;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CommitCardUseCaseTest {
    private WorkflowRepository workflowRepository;
    private HierarchyInitial hierarchyInitial;
    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;
    private String columnId;
    private String workflowId;
    private String boardId;

    @Before
    public void setUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();

        domainEventBus = new DomainEventBus();
        domainEventBus.register(new DomainEventHandler(workflowRepository, boardRepository, domainEventBus));

        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        boardId = hierarchyInitial.CreateBoard();
        workflowId = hierarchyInitial.CreateWorkflow(boardId);
        columnId = hierarchyInitial.CreateColumn(workflowId);
    }

    @Test
    public void testCommitCard(){
        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        Column column = workflow.findColumnById(columnId);

        assertEquals(0, column.getCommittedCards().size());

        Card card = new Card(UUID.randomUUID().toString(), "Card", boardId, workflowId, columnId);

        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository, domainEventBus);
        CommitCardInput commitCardInput = new CommitCardInput(card.getId(), workflowId, columnId);
        CommitCardOutput commitCardOutput = new CommitCardOutput();

        commitCardUseCase.execute(commitCardInput, commitCardOutput);

        assertEquals(1, column.getCommittedCards().size());
    }
}
