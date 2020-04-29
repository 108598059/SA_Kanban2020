package ddd.kanban.usecase.board;

import ddd.kanban.adapter.repository.board.InMemoryBoardRepository;
import ddd.kanban.adapter.repository.board.SqliteBoardRepository;
import ddd.kanban.usecase.board.create.CreateBoardInput;
import ddd.kanban.usecase.board.create.CreateBoardOutput;
import ddd.kanban.usecase.board.create.CreateBoardUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CreateBoardUseCaseTest {
    private BoardRepository inMemoryBoardRepository;
    private BoardRepository sqliteBoardRepository;
    private String boardId;

    @Before
    public void setUp(){
        boardId = UUID.randomUUID().toString();
        inMemoryBoardRepository = new InMemoryBoardRepository();
        sqliteBoardRepository = new SqliteBoardRepository();
    }

    @Test
    public void testCreateBoardUseCase(){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(inMemoryBoardRepository);
        CreateBoardInput createBoardInput = new CreateBoardInput("TestBoard","This is board that save in memory");
        CreateBoardOutput createBoardOutput = new CreateBoardOutput();
        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        assertEquals("TestBoard", createBoardOutput.getBoardName());
        assertEquals("This is board that save in memory", createBoardOutput.getBoardDescription());
    }

    @Test
    public void testCreateBoardUseCaseWithSQLite(){
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(sqliteBoardRepository);
        CreateBoardInput createBoardInput = new CreateBoardInput("TestBoard2", "This is board that save in sqlite");
        CreateBoardOutput createBoardOutput = new CreateBoardOutput();
        createBoardUseCase.execute(createBoardInput, createBoardOutput);
        assertEquals("TestBoard2", createBoardOutput.getBoardName());
        assertEquals("This is board that save in sqlite", createBoardOutput.getBoardDescription());
    }
}
