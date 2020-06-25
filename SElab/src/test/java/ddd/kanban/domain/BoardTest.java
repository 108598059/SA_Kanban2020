package ddd.kanban.domain;

import ddd.kanban.domain.model.kanbanboard.board.Board;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void testCreateBoardShouldGenerateBoardCreatedDomainEvent(){
        String boardId = UUID.randomUUID().toString();
        Board board = new Board(boardId, "BoardName", "Board Description");

        assertEquals(1, board.getDomainEvents().size());
        board.clearDomainEvents();
        assertEquals(0, board.getDomainEvents().size());
    }
}
