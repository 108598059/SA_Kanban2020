package ddd.kanban.usecase.kanbanboard.workflow.create;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.workflow.InMemoryWorkflowRepository;
import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.HierarchyInitial;
import ddd.kanban.usecase.repository.BoardRepository;
import ddd.kanban.usecase.kanbanboard.workflow.mapper.WorkflowEntityMapper;
import ddd.kanban.usecase.repository.WorkflowRepository;
import org.junit.Before;
import org.junit.Test;

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
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInput("Workflow 1", boardId);
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutput();

        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(createWorkflowOutput.getWorkflowId()));

        assertEquals(createWorkflowOutput.getWorkflowId(), workflow.getId());
        assertEquals("Workflow 1", workflow.getTitle());
    }

    @Test
    public void testCreateWorkflowShouldCreateDefaultColumn(){
        String boardId = hierarchyInitial.CreateBoard();
        String workflowId = hierarchyInitial.CreateWorkflow(boardId);

        Workflow workflow = WorkflowEntityMapper.mappingWorkflowFrom(workflowRepository.findById(workflowId));
        assertEquals(1, workflow.getColumns().size());
    }
}
