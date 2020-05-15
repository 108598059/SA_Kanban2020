package phd.sa.csie.ntut.edu.tw.domain.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Column;

public class ColumnTest {

  @Test
  public void createColumn() {
    Column column = new Column("Developing");

    assertEquals("Developing", column.getTitle());
  }

}