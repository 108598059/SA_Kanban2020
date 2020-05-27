package ddd.kanban.usecase.workflow.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.workflow.create.*;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class CreateColumnUseCaseTest {
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private String workflowId;
    private String boardId;
    private HierarchyInitial hierarchyInitial;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        this.domainEventBus = new DomainEventBus();
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
        boardId = hierarchyInitial.CreateBoard();
        workflowId = hierarchyInitial.CreateWorkflow(boardId);

    }


    @Test
    public void testCreateColumn(){
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(workflowRepository);
        CreateColumnInput createColumnInput = new CreateColumnInput("column", workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        assertNotNull(createColumnOutput.getColumnId());
    }
}
