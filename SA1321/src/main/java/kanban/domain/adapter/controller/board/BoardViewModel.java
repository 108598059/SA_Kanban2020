package kanban.domain.adapter.controller.board;

import kanban.domain.usecase.entity.BoardEntity;

import java.util.List;

public class BoardViewModel {
    private List<BoardEntity> boardEntities;

    public List<BoardEntity> getBoardEntities() {
        return boardEntities;
    }

    public void setBoardEntities(List<BoardEntity> boardEntities) {
        this.boardEntities = boardEntities;
    }
}
