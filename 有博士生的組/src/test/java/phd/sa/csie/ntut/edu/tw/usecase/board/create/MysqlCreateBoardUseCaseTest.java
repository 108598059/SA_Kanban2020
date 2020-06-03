package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import phd.sa.csie.ntut.edu.tw.adapter.database.DB_connector;
import phd.sa.csie.ntut.edu.tw.adapter.presenter.board.create.CreateBoardPresenter;
import phd.sa.csie.ntut.edu.tw.adapter.repository.mysql.board.MysqlBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.board.BoardCreatedEventHandler;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class MysqlCreateBoardUseCaseTest {
    private BoardRepository boardRepository;
    private DomainEventBus eventBus;
    private String boardID;

    @Before
    public void setUp() {
        this.boardRepository = new MysqlBoardRepository();
        this.eventBus = new DomainEventBus();
        BoardCreatedEventHandler boardCreatedEventHandler = new BoardCreatedEventHandler(this.eventBus, this.boardRepository);

        this.eventBus.register(boardCreatedEventHandler);
    }

    @After
    public void tearDown() {
        BoardDTO boardDTO = this.boardRepository.findByID(this.boardID);
        try {
            Connection connection = DB_connector.getConnection();
            for (int i = 0; i < boardDTO.getColumnDTOs().size(); ++i) {
                PreparedStatement deleteColStmt = connection.prepareStatement("DELETE FROM `Column` WHERE `ID`=?");
                deleteColStmt.setString(1, boardDTO.getColumnDTOs().get(i).getID());
                deleteColStmt.executeUpdate();
            }

            PreparedStatement deleteBoardStmt = connection.prepareStatement("DELETE FROM `Board` WHERE `ID`=?");
            deleteBoardStmt.setString(1, boardDTO.getID());
            deleteBoardStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void create_board() {
        CreateBoardUseCase createBoardUseCase = new CreateBoardUseCase(this.eventBus, this.boardRepository);
        CreateBoardUseCaseInput createBoardUseCaseInput = new CreateBoardUseCaseInput();
        CreateBoardUseCaseOutput createBoardUseCaseOutput = new CreateBoardPresenter();

        UUID workspaceID = UUID.randomUUID();

        createBoardUseCaseInput.setBoardName("Software Architecture");
        createBoardUseCaseInput.setWorkspaceID(workspaceID.toString());

        createBoardUseCase.execute(createBoardUseCaseInput, createBoardUseCaseOutput);

        this.boardID = createBoardUseCaseOutput.getBoardID();
        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.boardID));

        assertEquals(2, board.getColumnNumber());
        assertEquals("Software Architecture", createBoardUseCaseOutput.getBoardName());
        assertEquals("Backlog", board.get(0).getTitle());
        assertEquals("Archive", board.get(board.getColumnNumber() - 1).getTitle());
        assertEquals(workspaceID, board.getWorkspaceID());
    }

}
