package domain.usecase.board;


import domain.adapters.controller.board.CreateBoardInputImpl;
import domain.adapters.controller.board.CreateBoardOutputImpl;
import domain.adapters.repository.BoardRepositoryImpl;
import domain.entity.DomainEventBus;
import domain.entity.board.Board;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.create.CreateBoardInput;
import domain.usecase.board.create.CreateBoardOutput;
import domain.usecase.board.create.CreateBoardUseCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CreateBoardTest {

    private DomainEventBus eventBus;
    private BoardRepository boardRepository;

    @Before
    public void setUp(){
        eventBus = new DomainEventBus();
        boardRepository = new BoardRepositoryImpl();
    }

    @Test
    public void createBoardTest(){


        CreateBoardInput createBoardInput = new CreateBoardInputImpl();
        CreateBoardOutput createBoardOutput = new CreateBoardOutputImpl();
        createBoardInput.setName("kanban");
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(boardRepository, eventBus);

        createBoardUseCase.execute(createBoardInput,createBoardOutput);

        Board board = BoardTransformer.toBoard(boardRepository.getBoardById(createBoardOutput.getBoardId()));

        assertEquals("kanban", board.getName());
        assertEquals(createBoardOutput.getBoardId(), board.getId());

    }
}
