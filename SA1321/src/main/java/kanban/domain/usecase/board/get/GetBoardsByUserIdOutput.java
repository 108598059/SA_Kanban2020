package kanban.domain.usecase.board.get;

import kanban.domain.usecase.entity.BoardEntity;

import java.util.List;

public interface GetBoardsByUserIdOutput {


    public List<BoardEntity> getBoardEntities();

    public void setBoardEntities(List<BoardEntity> boardEntities);
}
