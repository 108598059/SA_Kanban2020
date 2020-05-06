package domain.usecase.board.create;

import domain.adapter.repository.board.MySqlBoardRepository;
import domain.model.aggregate.board.Board;
import domain.usecase.BoardDTO;
import domain.usecase.board.repository.IBoardRepository;
import domain.usecase.entity.BoardEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateBoardUseCaseTest {
    @Test
    public void createBoardTest(){
        IBoardRepository boardRepository = new MySqlBoardRepository();
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);
        CreateBoardUseCaseInput input = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput output = new CreateBoardUseCaseOutput();

        input.setBoardName("Kanban of KanbanDevelopment");

        createBoardUseCase.execute(input, output);

        assertNotNull(output.getBoardId());
        assertEquals("Kanban of KanbanDevelopment", output.getBoardName());

        Board board = BoardDTO.BoardEntityToBoard(boardRepository.getBoardById(output.getBoardId()));

        assertEquals(output.getBoardId(), board.getBoardId());
        assertEquals(output.getBoardName(), board.getBoardName());
    }
}
