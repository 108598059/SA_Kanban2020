package ddd.kanban.usecase.kanbanboard.workflow.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.workflow.Column;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class CreateWorkflowUseCaseTest {
    private WorkflowRepository workflowRepository;
    private BoardRepository boardRepository;
    private HierarchyInitial hierarchyInitial;
    private DomainEventBus domainEventBus;

    @Before
    public void setUp(){
        workflowRepository = new InMemoryWorkflowRepository();
        boardRepository = new InMemoryBoardRepository();
        domainEventBus = new DomainEventBus();
        hierarchyInitial = new HierarchyInitial(boardRepository, workflowRepository, domainEventBus);
    }

    @Test
    public void testCreateWorkflow() {
        String boardId = hierarchyInitial.CreateBoard();
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository, domainEventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput("workflowTitle", boardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(createWorkflowOutput.getWorkflowId()));

        assertEquals(createWorkflowOutput.getWorkflowId(), workflow.getId());
        assertEquals(createWorkflowOutput.getWorkflowTitle(), "workflowTitle");
        assertEquals("workflowTitle", workflow.getTitle());
        assertEquals(createWorkflowOutput.getWorkflowBoardId(), workflow.getBoardId());
    }

    @Test
    public void testCreateWorkflowShouldCreateDefaultColumn(){
        String boardId = hierarchyInitial.CreateBoard();
        String workflowId = hierarchyInitial.CreateWorkflow(boardId);

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        assertEquals(1, workflow.getColumns().size());

        List<Column> columns = workflow.getColumns();
        Column defaultColumn = columns.get(0);
        assertEquals("Default Column", defaultColumn.getTitle());
    }
}
