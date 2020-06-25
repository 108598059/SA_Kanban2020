package tw.teddysoft.clean.usecase.kanbanboard.workflow;

import org.junit.Before;
import org.junit.Test;
import tw.teddysoft.clean.adapter.gateway.kanbanboard.InMemoryAggregateRootRepositoryPeer;
import tw.teddysoft.clean.adapter.presenter.kanbanboard.lane.SingleStagePresenter;
import tw.teddysoft.clean.adapter.presenter.kanbanboard.workflow.SingleWorkflowPresenter;
import tw.teddysoft.clean.domain.model.DomainEventBus;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Lane;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.LaneOrientation;
import tw.teddysoft.clean.domain.model.kanbanboard.workflow.Workflow;
import tw.teddysoft.clean.domain.usecase.UseCase;
import tw.teddysoft.clean.domain.usecase.repository.Repository;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageInput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageOutput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.stage.create.CreateStageUseCase;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneInput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneOutput;
import tw.teddysoft.clean.usecase.kanbanboard.lane.swimlane.create.CreateSwimlaneUseCase;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowInput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowOutput;
import tw.teddysoft.clean.usecase.kanbanboard.workflow.create.CreateWorkflowUseCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateLaneTest {

    private Workflow workflow;
    private Repository<Workflow> workflowRepository;
    private DomainEventBus eventBus;

    @Before
    public void setUp(){

        workflowRepository = new Repository(new InMemoryAggregateRootRepositoryPeer());
        eventBus = new DomainEventBus();

        String workflowId = createWorkflow("0000001", "default_workflow");
        workflow = workflowRepository.findById(workflowId);
        assertNotNull(workflow);
    }

    @Test
    public void create_top_stage(){
        create_top_stage(workflow.getId(), "Backlog");
        assertEquals(1, workflow.getStages().size());
        assertEquals(LaneOrientation.VERTICAL, workflow.getStages().get(0).getOrientation());
        assertEquals("Backlog", workflow.getStages().get(0).getName());
    }

    @Test
    public void create_two_stage_under_Backlog_stage(){
        create_top_stage(workflow.getId(), "Backlog");
        Lane backlog = workflow.getStages().get(0);

        createStage(workflow.getId(), "Legend", backlog.getId());
        assertEquals(1, backlog.getChildren().size());
        assertEquals(LaneOrientation.VERTICAL, backlog.getChildren().get(0).getOrientation());
        assertEquals("Legend", backlog.getChildren().get(0).getName());

        createStage(workflow.getId(), "Ready", backlog.getId());
        assertEquals(LaneOrientation.VERTICAL, backlog.getChildren().get(1).getOrientation());
        assertEquals("Ready", backlog.getChildren().get(1).getName());
    }

    @Test
    public void create_two_swimlanes_under_Backlog_stage(){
        create_top_stage(workflow.getId(), "Backlog");
        Lane backlog = workflow.getStages().get(0);

        createSwimlane(workflow.getId(), "Top5", backlog.getId());
        assertEquals(1, backlog.getChildren().size());
        assertEquals(LaneOrientation.HORIZONTAL, backlog.getChildren().get(0).getOrientation());
        assertEquals("Top5", backlog.getChildren().get(0).getName());

        createSwimlane(workflow.getId(), "Idea", backlog.getId());
        assertEquals(2, backlog.getChildren().size());
        assertEquals(LaneOrientation.HORIZONTAL, backlog.getChildren().get(1).getOrientation());
        assertEquals("Idea", backlog.getChildren().get(1).getName());

    }



    @Test
    public void create_business_process_maintenance_stage(){
        // create a stage like : https://reurl.cc/1QEryG

        create_top_stage(workflow.getId(), "Operations - Business Process Maintenance");
        Lane operation = workflow.getStages().get(0);

        String productionProblemId = createSwimlane(workflow.getId(), "Production Problem", operation.getId());
            createStage(workflow.getId(), "New", productionProblemId);
            String workingID = createStage(workflow.getId(), "Working", productionProblemId);
                createStage(workflow.getId(), "Find Cause", workingID);
                createStage(workflow.getId(), "Fix Cause", workingID);
            createStage(workflow.getId(), "Done", productionProblemId);

        String plannedBusinessNeedId = createSwimlane(workflow.getId(), "Planned Business Need", operation.getId());
            String due2Months = createStage(workflow.getId(), "Due 2 months", plannedBusinessNeedId);
                createSwimlane(workflow.getId(), "High Impact", due2Months);
                createSwimlane(workflow.getId(), "Low Impact", due2Months);
            String due1Month = createStage(workflow.getId(), "Due 1 month", plannedBusinessNeedId);
                createSwimlane(workflow.getId(), "High Impact", due1Month);
                createSwimlane(workflow.getId(), "Low Impact", due1Month);
            String due1week = createStage(workflow.getId(), "Due 1 week", plannedBusinessNeedId);
                createSwimlane(workflow.getId(), "High Impact", due1week);
                createSwimlane(workflow.getId(), "Low Impact", due1week);
            String inWork = createStage(workflow.getId(), "In Work", plannedBusinessNeedId);
                createSwimlane(workflow.getId(), "High Impact", inWork);
                createSwimlane(workflow.getId(), "Low Impact", inWork);
            String done = createStage(workflow.getId(), "Done", plannedBusinessNeedId);
                createSwimlane(workflow.getId(), "High Impact", done);
                createSwimlane(workflow.getId(), "Low Impact", done);

        String routineId = createSwimlane(workflow.getId(), "Routine", operation.getId());

        String unplannedId = createSwimlane(workflow.getId(), "Unplanned", operation.getId());
            createStage(workflow.getId(), "New", unplannedId);
            createStage(workflow.getId(), "Committed", unplannedId);
            createStage(workflow.getId(), "In Work", unplannedId);
            createStage(workflow.getId(), "Test", unplannedId);
            createStage(workflow.getId(), "Done", unplannedId);

        String platformImprovementsId = createSwimlane(workflow.getId(), "Platform Improvements", operation.getId());
            createStage(workflow.getId(), "New", platformImprovementsId);
            createStage(workflow.getId(), "Committed", platformImprovementsId);
            createStage(workflow.getId(), "In Work", platformImprovementsId);
            createStage(workflow.getId(), "Test", platformImprovementsId);
            createStage(workflow.getId(), "Done", platformImprovementsId);

        workflow.dumpLane();
        assertEquals(36, workflow.getTotalLaneSize());
    }

    @Test
    public void create_devops_workflow(){
        // create a workflow like : https://reurl.cc/31W81L ,
        //                          https://reurl.cc/L1gvWL , and
        //                          https://reurl.cc/algpzG

        String devQueueId = create_top_stage(workflow.getId(), "Dev Queue");
            String helpId = createStage(workflow.getId(), "Help", devQueueId);
                createSwimlane(workflow.getId(), "Legend", helpId);
                createSwimlane(workflow.getId(), "Templates", helpId);
            createStage(workflow.getId(), "Backlog", devQueueId);
            createStage(workflow.getId(), "Up Next", devQueueId);

        String devInFightId = create_top_stage(workflow.getId(), "Dev - In Flight");
            String epicInFlightId = createSwimlane(workflow.getId(), "Epic - In Flight", devInFightId);
                createStage(workflow.getId(), "Breakdown", epicInFlightId);
                createStage(workflow.getId(), "Doing", epicInFlightId);
                createStage(workflow.getId(), "Pending Ops", epicInFlightId);

            String expeditedId = createSwimlane(workflow.getId(), "Expedited", devInFightId);
                createStage(workflow.getId(), "Doing", expeditedId);
                createStage(workflow.getId(), "Testing", expeditedId);
                createStage(workflow.getId(), "Review", expeditedId);

            String standardId = createSwimlane(workflow.getId(), "Standard", devInFightId);
                createStage(workflow.getId(), "Doing", standardId);
                createStage(workflow.getId(), "Testing", standardId);
                createStage(workflow.getId(), "Review", standardId);

        String operationsQueue = create_top_stage(workflow.getId(), "Operations Queue");
            String help = createStage(workflow.getId(), "Help", operationsQueue);
                createSwimlane(workflow.getId(), "Legend", help);
                 createSwimlane(workflow.getId(), "Templates", help);
            createStage(workflow.getId(), "Ops Backlog", operationsQueue);

        String opsInFlight = create_top_stage(workflow.getId(), "Ops - In Flight");
            createStage(workflow.getId(), "Up Next", opsInFlight);
        String doingId = createStage(workflow.getId(), "Doing", opsInFlight);
            String keepTheLightsOnId = createSwimlane(workflow.getId(), "Keep The Lights On", doingId);
                createStage(workflow.getId(), "Doing", keepTheLightsOnId);
                createStage(workflow.getId(), "Testing", keepTheLightsOnId);
        String expeditedId2 = createSwimlane(workflow.getId(), "Expedited", doingId);
            createStage(workflow.getId(), "Doing", expeditedId2);
            createStage(workflow.getId(), "Testing", expeditedId2);
        String standardId2 = createSwimlane(workflow.getId(), "Standard", doingId);
            createStage(workflow.getId(), "Doing", standardId2);
            createStage(workflow.getId(), "Testing", standardId2);

        create_top_stage(workflow.getId(), "Completed");

        String finishedId = create_top_stage(workflow.getId(), "Finished - Ready to Archive");
            createSwimlane(workflow.getId(), "Finished As Planned", finishedId);
            createSwimlane(workflow.getId(), "Started bu not Finished", finishedId);
            createSwimlane(workflow.getId(), "Discarded Requests / Ideas", finishedId);

        workflow.dumpLane();
        assertEquals(41, workflow.getTotalLaneSize());
    }


    private String createWorkflow(String boardId, String name) {

        UseCase<CreateWorkflowInput, CreateWorkflowOutput> createWorkflowUC =
                new CreateWorkflowUseCase(workflowRepository, eventBus);

        CreateWorkflowInput input = createWorkflowUC.createInput();
        CreateWorkflowOutput output = new SingleWorkflowPresenter();
        input.setBoardId(boardId);
        input.setWorkflowName(name);

        createWorkflowUC.execute(input, output);
        return output.getWorkflowId();

    }

    private String create_top_stage(String workflowId, String name){
        return createStage(workflowId, name, null);
    }

    private String createStage(String workflowId, String name, String parentId){
        UseCase<CreateStageInput, CreateStageOutput> createStageLaneUC =
                new CreateStageUseCase(workflowRepository, eventBus);

        CreateStageInput input = createStageLaneUC.createInput();
        CreateStageOutput output = new SingleStagePresenter();
        input.setWorkflowId(workflowId);
        input.setName(name);
        input.setParentId(parentId);

        createStageLaneUC.execute(input, output);

        return output.getId();
    }

    private String createSwimlane(String workflowId, String name, String parentId){

        UseCase<CreateSwimlaneInput, CreateSwimlaneOutput> createSwimLaneUC =
                new CreateSwimlaneUseCase(workflowRepository, eventBus);

        CreateSwimlaneInput input = createSwimLaneUC.createInput();
        CreateSwimlaneOutput output = new SingleStagePresenter();

        input.setWorkflowId(workflowId);
        input.setParentId(parentId);
        input.setTitle(name);

        createSwimLaneUC.execute(input, output);

        return output.getId();
    }

}
