package kanban.domain;

import kanban.domain.adapter.presenter.board.create.CreateBoardPresenter;
import kanban.domain.adapter.presenter.card.create.CreateCardPresenter;
import kanban.domain.adapter.presenter.card.move.MoveCardPresenter;
import kanban.domain.adapter.presenter.stage.create.CreateStagePresenter;
import kanban.domain.adapter.presenter.workflow.create.CreateWorkflowPresenter;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.board.create.CreateBoardInput;
import kanban.domain.usecase.board.create.CreateBoardOutput;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.card.create.CreateCardInput;
import kanban.domain.usecase.card.create.CreateCardUseCase;
import kanban.domain.usecase.card.move.MoveCardInput;
import kanban.domain.usecase.card.move.MoveCardOutput;
import kanban.domain.usecase.card.move.MoveCardUseCase;
import kanban.domain.usecase.card.repository.ICardRepository;
import kanban.domain.usecase.stage.create.CreateStageInput;
import kanban.domain.usecase.stage.create.CreateStageOutput;
import kanban.domain.usecase.stage.create.CreateStageUseCase;
import kanban.domain.usecase.workflow.create.CreateWorkflowInput;
import kanban.domain.usecase.workflow.create.CreateWorkflowOutput;
import kanban.domain.usecase.workflow.create.CreateWorkflowUseCase;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

import static org.junit.Assert.assertEquals;

public class Utility {

    private IWorkflowRepository workflowRepository;
    private IBoardRepository boardRepository;
    private ICardRepository cardRepository;
    private DomainEventBus eventBus;

    public Utility(
            IBoardRepository boardRepository,
            IWorkflowRepository workflowRepository,
            DomainEventBus eventBus) {
        this.workflowRepository = workflowRepository;
        this.boardRepository = boardRepository;
        this.cardRepository = new InMemoryCardRepository();
        this.eventBus = eventBus;
    }

    public String createBoard(String boardName){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);
        CreateBoardInput input = createBoardUseCase;
        CreateBoardOutput output = new CreateBoardPresenter();
        input.setUserId("1");
        input.setBoardName(boardName);
        createBoardUseCase.execute(input, output);
        return output.getBoardId();
    }

    public String createWorkflow(String boardId, String workflowName){
        CreateWorkflowUseCase createWorkflowUseCase = new CreateWorkflowUseCase(
                workflowRepository,
                eventBus);

        CreateWorkflowInput input = createWorkflowUseCase;
        input.setBoardId(boardId);
        input.setWorkflowName(workflowName);

        CreateWorkflowOutput output = new CreateWorkflowPresenter();
        createWorkflowUseCase.execute(input, output);
        return output.getWorkflowId();
    }

    public String createStage(String workflowId, String stageName) {

        CreateStageUseCase createStageUseCase = new CreateStageUseCase(workflowRepository, eventBus);
        CreateStageInput input = createStageUseCase;
        input.setStageName(stageName);
        input.setWorkflowId(workflowId);

        CreateStageOutput output = new CreateStagePresenter();
        createStageUseCase.execute(input, output);
        return output.getStageId();
    }


    public String createCard(String workflowId, String stageId, String cardName, String description,
                             String type, String size) {

        CreateCardUseCase createCardUseCase = new CreateCardUseCase(cardRepository,eventBus);

        CreateCardInput input = new CreateCardInput();
        input.setCardName(cardName);
        input.setDescription(description);
        input.setType(type);
        input.setSize(size);
        input.setWorkflowId(workflowId);
        input.setStageId(stageId);
        CreateCardPresenter output = new CreateCardPresenter();

        createCardUseCase.execute(input, output);
        return output.getCardId();
    }

    public String moveCard(String workflowId, String cardId, String originStageId, String newStageId) {
        MoveCardUseCase moveCardUseCase = new MoveCardUseCase(workflowRepository, eventBus);
        MoveCardInput input = moveCardUseCase;
        input.setCardId(cardId);
        input.setWorkflowId(workflowId);
        input.setOriginStageId(originStageId);
        input.setNewStageId(newStageId);
        MoveCardOutput output = new MoveCardPresenter();

        moveCardUseCase.execute(input, output);

        return output.getCardId();
    }
}
