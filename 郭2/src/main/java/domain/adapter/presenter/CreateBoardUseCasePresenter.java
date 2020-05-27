package domain.adapter.presenter;

import domain.adapter.BoardEntityDto;
import domain.adapter.view_model.CreateBoardViewModel;
import domain.usecase.board.create.CreateBoardUseCaseOutput;

public class CreateBoardUseCasePresenter implements Presenter<CreateBoardViewModel>, CreateBoardUseCaseOutput {
    BoardEntityDto boardEntityDto;

    public CreateBoardUseCasePresenter() {
        boardEntityDto = new BoardEntityDto();
    }

    @Override
    public CreateBoardViewModel createView() {
        CreateBoardViewModel viewModel = new CreateBoardViewModel();
        viewModel.setViewModel(boardEntityDto);
        return viewModel;
    }

    @Override
    public void setBoardId(String boardId) {
        boardEntityDto.setBoardId(boardId);
    }

    @Override
    public String getBoardId() {
        return boardEntityDto.getBoardId();
    }

    @Override
    public void setBoardName(String boardName) {
        boardEntityDto.setBoardName(boardName);
    }

    @Override
    public String getBoardName() {
        return boardEntityDto.getBoardName();
    }
}
