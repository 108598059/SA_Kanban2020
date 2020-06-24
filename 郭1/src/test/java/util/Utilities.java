package util;

import domain.adapters.controller.board.input.CreateBoardInputImpl;
import domain.adapters.controller.board.output.CreateBoardOutputImpl;
import domain.adapters.controller.card.input.CalculateCycleTimeInputImpl;
import domain.adapters.controller.card.input.CreateCardInputImpl;
import domain.adapters.controller.card.input.MoveCardInputImpl;
import domain.adapters.controller.card.output.CalculateCycleTimeOutputImpl;
import domain.adapters.controller.card.output.CreateCardOutputImpl;
import domain.adapters.controller.card.output.MoveCardOutputImpl;
import domain.adapters.controller.workflow.input.CreateStageInputImpl;
import domain.adapters.controller.workflow.input.CreateSwimlaneInputImpl;
import domain.adapters.controller.workflow.input.CreateWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CreateStageOutputImpl;
import domain.adapters.controller.workflow.output.CreateSwimlaneOutputImpl;
import domain.adapters.controller.workflow.output.CreateWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.Swimlane;
import domain.entity.aggregate.workflow.Workflow;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import domain.usecase.card.CardRepository;
import domain.usecase.card.create.CreateCardInput;
import domain.usecase.card.create.CreateCardOutput;
import domain.usecase.card.create.CreateCardUseCase;
import domain.usecase.cycleTimeCalculator.calculate.CalculateCycleTimeInput;
import domain.usecase.cycleTimeCalculator.calculate.CalculateCycleTimeOutput;
import domain.usecase.cycleTimeCalculator.calculate.CalculateCycleTimeUseCase;
import domain.usecase.cycleTimeCalculator.CycleTime;
import domain.usecase.card.move.MoveCardInput;
import domain.usecase.card.move.MoveCardOutput;
import domain.usecase.card.move.MoveCardUseCase;
import domain.usecase.flowevent.FlowEventRepository;
import domain.usecase.workflow.WorkflowTransformer;
import domain.usecase.workflow.create.CreateStageInput;
import domain.usecase.workflow.create.CreateStageOutput;
import domain.usecase.workflow.create.CreateStageUseCase;
import domain.usecase.workflow.create.CreateSwimlaneInput;
import domain.usecase.workflow.create.CreateSwimlaneOutput;
import domain.usecase.workflow.create.CreateSwimlaneUseCase;
import domain.usecase.workflow.WorkflowRepository;
import domain.usecase.workflow.create.CreateWorkflowInput;
import domain.usecase.workflow.create.CreateWorkflowOutput;
import domain.usecase.workflow.create.CreateWorkflowUseCase;

import java.util.ArrayList;
import java.util.List;

public class Utilities {
    private BoardRepository boardRepository;
    private WorkflowRepository workflowRepository;
    private CardRepository cardRepository;
    private FlowEventRepository flowEventRepository;

    private DomainEventBus eventBus;
    private Workflow workflow;

    private List<String> cardId;

    private String boardId;
    private String workflowId;
    private String readyStageId;
    private String analysisStageId;
    private String developmentStageId;
    private String testStageId;

    private String readySwimlaneId;
    private String analysisSwimlaneId;

    private String developmentSwimlaneId;
    private String testSwimlaneId;


    public Utilities(FlowEventRepository flowEventRepository, BoardRepository boardRepository, WorkflowRepository workflowRepository, CardRepository cardRepository, DomainEventBus eventBus){
        this.boardRepository = boardRepository;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
        this.flowEventRepository = flowEventRepository;
        this.eventBus = eventBus;
        this.cardId = new ArrayList<String>();

    }

    public void createBoardAndWorkflowAndStageAndSwimlane() {

        boardId = createBoard("kanbanboard");
        workflowId = createWorkflow(boardId, "kanbanWorkflow");
        workflow = WorkflowTransformer.toWorkflow(workflowRepository.getWorkFlowById(workflowId));

        readyStageId = createStage(workflowId ,"readyStage");
        analysisStageId = createStage(workflowId, "analysisStage");
        developmentStageId = createStage(workflowId, "developmentStage");
        testStageId = createStage(workflowId, "testStage");

        readySwimlaneId = createSwimlane(workflowId, readyStageId, "readySwimlane");
        analysisSwimlaneId = createSwimlane(workflowId, analysisStageId, "analysisSwimlane");
        developmentSwimlaneId = createSwimlane(workflowId, developmentStageId, "developmentSwimlane");
        testSwimlaneId = createSwimlane(workflowId, testStageId, "testSwimlane");

    }

    public void createCardOnFirstSwimlane(String[] strings) {
        for(String name : strings){
            cardId.add(createCard(name));
        }
    }

    public String createBoard(String name){
        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName(name);
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        createBoardUseCase.execute(createBoardInput,createBoardOutput);
        return createBoardOutput.getBoardId();
    }
    public String createWorkflow(String boardId, String name){

        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(workflowRepository,eventBus);
        CreateWorkflowInput createWorkflowInput = new CreateWorkflowInputImpl();
        CreateWorkflowOutput createWorkflowOutput = new CreateWorkflowOutputImpl();
        createWorkflowInput.setWorkflowName(name);
        createWorkflowInput.setBoardId(boardId);
        createWorkflowUseCase.execute(createWorkflowInput, createWorkflowOutput);
        return createWorkflowOutput.getWorkflowId();
    }
    public String createStage(String id, String name){
        CreateStageUseCase createStage = new CreateStageUseCase(workflowRepository, eventBus) ;
        CreateStageInput createStageInput = new CreateStageInputImpl() ;
        CreateStageOutput createStageOutput = new CreateStageOutputImpl() ;
        createStageInput.setWorkflowId(id);
        createStageInput.setStageName(name);
        createStage.execute( createStageInput, createStageOutput ) ;
        return createStageOutput.getStageId();
    }
    public String createSwimlane(String workflowId, String stageId, String name){
        CreateSwimlaneUseCase createSwimlaneUseCase = new CreateSwimlaneUseCase(workflowRepository, eventBus);
        CreateSwimlaneInput createSwimlaneInput = new CreateSwimlaneInputImpl();
        CreateSwimlaneOutput createSwimlaneOutput = new CreateSwimlaneOutputImpl();
        createSwimlaneInput.setName(name);
        createSwimlaneInput.setStageId(stageId);
        createSwimlaneInput.setWorkflowId(workflowId);
        createSwimlaneUseCase.execute(createSwimlaneInput, createSwimlaneOutput);
        return createSwimlaneOutput.getSwimlaneId();
    }
    public String createCard(String name){
        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);
        CreateCardInput createCardInput = new CreateCardInputImpl();
        CreateCardOutput createCardOutput = new CreateCardOutputImpl();
        createCardInput.setCardName(name) ;
        createCardInput.setWorkflowId(workflowId);
        createCardInput.setStageId(readyStageId);
        createCardInput.setSwimlaneId(readySwimlaneId);
        createCardUseCase.execute( createCardInput, createCardOutput) ;
        return createCardOutput.getCardId();
    }
    public void moveCard(String workflowId, String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId, String cardId){
        MoveCardInput moveCardUseCaseInput = new MoveCardInputImpl();
        MoveCardOutput moveCardUseCaseOutput = new MoveCardOutputImpl();
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);

        moveCardUseCaseInput.setWorkflowId(workflowId);
        moveCardUseCaseInput.setFromStageId(fromStageId);
        moveCardUseCaseInput.setToStageId(toStageId);
        moveCardUseCaseInput.setFromSwimlaneId(fromSwimlaneId);
        moveCardUseCaseInput.setToSwimlaneId(toSwimlaneId);
        moveCardUseCaseInput.setCardId(cardId);

        moveCardUseCase.execute(moveCardUseCaseInput, moveCardUseCaseOutput);
    }
    public CycleTime calculateCycleTime(String workflowId, String fromStageId, String toStageId, String fromSwimlaneId, String toSwimlaneId, String cardId){

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository, eventBus);
        CalculateCycleTimeInput input = new CalculateCycleTimeInputImpl();
        CalculateCycleTimeOutput output = new CalculateCycleTimeOutputImpl();

        input.setWorkflowId(workflowId);
        input.setCardId(cardId);
        input.setFromStageId(fromStageId);
        input.setToStageId(toStageId);
        input.setFromSwimlane(fromSwimlaneId);
        input.setToSwimlane(toSwimlaneId);

        calculateCycleTimeUseCase.execute(input, output);
        return output.getCycleTime();
    }

    public CardRepository getCardRepository() {
        return cardRepository;
    }

    public Swimlane getReady() {
        return workflowRepository.getWorkFlowById(workflowId).getStageById(readyStageId).getSwimlaneById(readySwimlaneId);
    }

    public Swimlane getAnalysis() {
        return workflowRepository.getWorkFlowById(workflowId).getStageById(analysisStageId).getSwimlaneById(analysisSwimlaneId);
    }

    public WorkflowRepository getWorkflowRepository() {
        return workflowRepository;
    }

    public DomainEventBus getEventBus() {
        return eventBus;
    }

    public List<String> getCardId(){
        return cardId;
    }

    public Workflow getWorkflow() {
        return workflow;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public String getReadyStageId() {
        return readyStageId;
    }

    public String getAnalysisStageId() {
        return analysisStageId;
    }

    public String getDevelopmentStageId() {
        return developmentStageId;
    }

    public String getTestStageId() {
        return testStageId;
    }

    public String getReadySwimlaneId() {
        return readySwimlaneId;
    }

    public String getAnalysisSwimlaneId() {
        return analysisSwimlaneId;
    }

    public String getDevelopmentSwimlaneId() {
        return developmentSwimlaneId;
    }

    public String getTestSwimlaneId() {
        return testSwimlaneId;
    }

}
