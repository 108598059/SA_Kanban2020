package tw.teddysoft.clean.usecase.kanbanboard.workflow;

import org.junit.Before;
import org.junit.Test;
import tw.teddysoft.clean.adapter.gateway.kanbanboard.InMemoryAggregateRootRepositoryPeer;
import tw.teddysoft.clean.adapter.presenter.kanbanboard.workflow.SingleWorkflowPresenter;
import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.domain.usecase.repository.Repository;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowInput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowOutput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowUseCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateWorkflowUseCaseTest {

    private Repository<Workflow> workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        workflowRepository = new Repository(new InMemoryAggregateRootRepositoryPeer());
        assertEquals(0, workflowRepository.findAll().size());

        eventBus = new DomainEventBus();
    }

    @Test
    public void creating_a_workflow_should_success() {

        UseCase<CreateWorkflowInput, CreateWorkflowOutput> createWorkflowUC =
                new CreateWorkflowUseCase(workflowRepository, eventBus);

        CreateWorkflowInput input = createWorkflowUC.createInput();
        CreateWorkflowOutput output = new SingleWorkflowPresenter();
        input.setBoardId("boardId_000001");
        input.setWorkflowName("default_workflow");

        createWorkflowUC.execute(input, output);

        assertNotNull(output.getWorkflowId());
        assertEquals(1, workflowRepository.findAll().size());
        assertEquals("boardId_000001", workflowRepository.findById(output.getWorkflowId()).getBoardId());
    }

}
