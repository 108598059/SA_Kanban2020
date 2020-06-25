package phd.sa.csie.ntut.edu.tw.usecase.column.setwip;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.adapter.repository.memory.board.MemoryBoardRepository;
import phd.sa.csie.ntut.edu.tw.model.domain.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.model.aggregate.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.column.create.CreateColumnUseCaseOutput;
import phd.sa.csie.ntut.edu.tw.usecase.repository.board.BoardRepository;

public class SetColumnWIPTest {
    private BoardRepository boardRepository;
    private Board board;
    private String columnID;
    private DomainEventBus eventBus;

    @Before
    public void add_a_column_to_a_board() {
        this.boardRepository = new MemoryBoardRepository();
        this.eventBus = new DomainEventBus();

        Board board = new Board(UUID.randomUUID(), "Software Architecture");
        UUID boardID = board.getID();
        this.boardRepository.save(BoardDTOConverter.toDTO(board));

        this.board = BoardDTOConverter.toEntity(this.boardRepository.findByID(boardID.toString()));

        CreateColumnUseCase createColumnUseCase = new CreateColumnUseCase(this.eventBus, this.boardRepository);
        CreateColumnUseCaseInput createColumnUseCaseInput = new CreateColumnUseCaseInput();
        CreateColumnUseCaseOutput createColumnUseCaseOutput = new CreateColumnUseCaseOutput();

        createColumnUseCaseInput.setBoardID(this.board.getID().toString());
        createColumnUseCaseInput.setColumnTitle("develop");
        createColumnUseCaseInput.setColumnIndex(0);

        createColumnUseCase.execute(createColumnUseCaseInput, createColumnUseCaseOutput);

        this.columnID = createColumnUseCaseOutput.getID();
    }

    @Test
    public void set_WIP_with_a_positive_number_3() {
        SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.eventBus, this.boardRepository);
        SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
        SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

        setColumnWIPUseCaseInput.setBoardID(this.board.getID().toString());
        setColumnWIPUseCaseInput.setColumnID(this.columnID);
        setColumnWIPUseCaseInput.setColumnWIP(3);

        setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);

        assertNotNull(setColumnWIPUseCaseOutput.getColumnID());
        assertEquals(3, setColumnWIPUseCaseOutput.getColumnWIP());

        Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(this.board.getID().toString()));

        assertEquals(3, board.get(0).getWIP());
    }

    @Test
    public void set_column_wip_is_negative_should_raise_illegal_argument_exception() {
        SetColumnWIPUseCase setColumnWIPUseCase = new SetColumnWIPUseCase(this.eventBus, this.boardRepository);
        SetColumnWIPUseCaseInput setColumnWIPUseCaseInput = new SetColumnWIPUseCaseInput();
        SetColumnWIPUseCaseOutput setColumnWIPUseCaseOutput = new SetColumnWIPUseCaseOutput();

        setColumnWIPUseCaseInput.setBoardID(this.board.getID().toString());
        setColumnWIPUseCaseInput.setColumnID(this.columnID);
        setColumnWIPUseCaseInput.setColumnWIP(-1);

        try {
            setColumnWIPUseCase.execute(setColumnWIPUseCaseInput, setColumnWIPUseCaseOutput);
        } catch (IllegalArgumentException e) {
            assertEquals("Column WIP should not be negative", e.getMessage());
            return;
        }
        fail("Column WIP is negative should raise IllegalArgumentException");
    }
}