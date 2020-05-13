package kanban.domain.usecase;

import kanban.domain.Utility;
import kanban.domain.adapter.controller.board.GetBoardsByUserIdPresenter;
import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.board.get.GetBoardsByUserIdInput;
import kanban.domain.usecase.board.get.GetBoardsByUserIdOutput;
import kanban.domain.usecase.board.get.GetBoardsByUserIdUseCase;
import kanban.domain.usecase.board.repository.IBoardRepository;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GetBoardTest {
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    private Utility utility;

    @Before
    public void setUp() {
//        workflowRepository = new InMemoryWorkflowRepository();
//        boardRepository = new InMemoryBoardRepository();

        workflowRepository = new MySqlWorkflowRepository();
        boardRepository = new MySqlBoardRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new DomainEventHandler(
                boardRepository,
                workflowRepository));

        utility = new Utility(boardRepository, workflowRepository, eventBus);
        utility.createBoard("boardName");
    }

    @Test
    public void Get_board_should_return_boardId() {

        GetBoardsByUserIdUseCase getBoardsByUserIdUseCase = new GetBoardsByUserIdUseCase(boardRepository);
        GetBoardsByUserIdInput input = new GetBoardsByUserIdInput();
        GetBoardsByUserIdOutput output = new GetBoardsByUserIdPresenter();

        input.setUserId("1");
        getBoardsByUserIdUseCase.execute(input, output);

        assertEquals(1, output.getBoardEntities().size());
        assertEquals("1", output.getBoardEntities().get(0).getUserId());
        assertEquals("boardName", output.getBoardEntities().get(0).getBoardName());
        assertEquals(0, output.getBoardEntities().get(0).getWorkflowIds().size());
    }
}
