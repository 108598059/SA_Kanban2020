package ddd.kanban.usecase.board;

import ddd.kanban.adapter.presenter.board.get.GetAllBoardsPresenter;
import ddd.kanban.adapter.repository.board.SqliteBoardRepository;
import ddd.kanban.usecase.board.get.GetAllBoardsInput;
import ddd.kanban.usecase.board.get.GetAllBoardsOutput;
import ddd.kanban.usecase.board.get.GetAllBoardsUseCase;
import ddd.kanban.usecase.repository.BoardRepository;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SqliteGetAllBoardsUseCaseTest {
    private BoardRepository boardRepository;

    public SqliteGetAllBoardsUseCaseTest(){
        this.boardRepository = new SqliteBoardRepository();
    }

    @Test
    public void testGetAllBoardsUseCase(){
        GetAllBoardsUseCase getAllBoardsUseCase = new GetAllBoardsUseCase(boardRepository);
        GetAllBoardsInput getAllBoardsInput = new GetAllBoardsInput();
        GetAllBoardsOutput getAllBoardsOutput = new GetAllBoardsPresenter();

        getAllBoardsUseCase.execute(getAllBoardsInput, getAllBoardsOutput);

        assertEquals(44 , getAllBoardsOutput.getBoards().size());
    }
}
