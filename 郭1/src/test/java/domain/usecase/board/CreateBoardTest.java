package domain.usecase.board;


import domain.adapters.controller.board.CreateBoardInputImpl;
import domain.adapters.controller.board.CreateBoardOutputImpl;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class CreateBoardTest {
    @Test
    public void createBoardTest(){

        BoardRepository boardRepository = new BoardRepositoryImpl();


        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("board1");
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository);

        createBoardUseCase.execute(createBoardInput,createBoardOutput);

        assertNotNull(createBoardOutput.getBoardId());

    }
}
