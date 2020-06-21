package ddd.kanban.usecase.kanbanboard.workflow.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.workflow.Lane;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.repository.WorkflowRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import org.junit.Before;
import org.junit.Test;

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
        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(workflowRepository, domainEventBus);
        CreateColumnInput createColumnInput = new CreateColumnInput("column", workflowId);
        CreateColumnOutput createColumnOutput = new CreateColumnOutput();

        createColumnUseCase.execute(createColumnInput, createColumnOutput);

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        Lane column = workflow.findColumnById(createColumnOutput.getColumnId());

        assertEquals(createColumnOutput.getColumnId(), column.getId());
    }
}
