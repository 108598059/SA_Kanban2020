package phd.sa.csie.ntut.edu.tw.model.board;

import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void testGetBacklogColumn() {
        Board board = new Board(UUID.randomUUID(), "Kanban");
        assertEquals("Backlog", board.getBacklogColumn().getTitle());
    }
}
