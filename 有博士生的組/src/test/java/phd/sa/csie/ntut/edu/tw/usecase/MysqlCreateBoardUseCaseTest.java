package phd.sa.csie.ntut.edu.tw.usecase;

import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.controller.repository.mysql.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MysqlCreateBoardUseCaseTest {
    private BoardRepository boardRepository;

    @Before
    public void setUp() {
        this.boardRepository = new MysqlBoardRepository();
    }

    @Test
    public void create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardUseCaseOutput();

        createBoardUseCaseInput.setBoardName("Software Architecture");

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        UUID boardId = UUID.fromString(createBoardUseCaseOutput.getBoardId());
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findById(boardId.toString()));

        assertEquals(2, board.getColumnNumber());
        assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
        assertEquals("Backlog", board.get(0).getTitle());
        assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
    }
}
