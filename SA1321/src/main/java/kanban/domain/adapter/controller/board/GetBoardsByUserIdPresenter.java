package kanban.domain.adapter.controller.board;

import kanban.domain.usecase.board.get.GetBoardsByUserIdOutput;
import kanban.domain.usecase.entity.BoardEntity;

import java.util.ArrayList;
import java.util.List;

public class GetBoardsByUserIdPresenter implements GetBoardsByUserIdOutput {
    private List<BoardEntity> boardEntities;
    public BoardViewModel build(){
        BoardViewModel viewModel = new BoardViewModel();
        viewModel.setBoardEntities(boardEntities);
        return viewModel;
    }

    @Override
    public List<BoardEntity> getBoardEntities() {
        return boardEntities;
    }

    @Override
    public void setBoardEntities(List<BoardEntity> boardEntities) {
        this.boardEntities = boardEntities;
    }


}
