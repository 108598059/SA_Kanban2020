package domain.usecase.board.create;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.model.DomainEventBus;
import domain.model.aggregate.board.Board;
import domain.usecase.board.BoardTransfer;
import domain.usecase.board.repository.IBoardRepository;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {

    @Test
    public void createBoardTest(){

        DomainEventBus eventBus = new DomainEventBus();
        IBoardRepository boardRepository = new MySqlBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository,eventBus);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput output = new CreateBoardUseCaseOutputImpl();

        input.setBoardName("Kanban of KanbanDevelopment");

        createBoardUseCase.execute(input, output);

        assertNotNull(output.getBoardId());
        assertEquals("Kanban of KanbanDevelopment", output.getBoardName());

        Board board = BoardTransfer.BoardDTOToBoard(boardRepository.getBoardById(output.getBoardId()));

        assertEquals(output.getBoardId(), board.getBoardId());
        assertEquals(output.getBoardName(), board.getBoardName());
    }
}
