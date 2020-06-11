package domain.adapter.presenter;

import domain.adapter.view_model.CreateBoardViewModel;
import domain.usecase.board.BoardDTO;
import domain.usecase.board.create.CreateBoardUseCaseOutput;

public class CreateBoardUseCasePresenter implements Presenter<CreateBoardViewModel>, CreateBoardUseCaseOutput {
    BoardDTO boardDTO;

    public CreateBoardUseCasePresenter() {
        boardDTO = new BoardDTO();
    }

    @Override
    public CreateBoardViewModel createViewModel() {
        CreateBoardViewModel viewModel = new CreateBoardViewModel();
        viewModel.setViewModel(boardDTO);
        return viewModel;
    }

    @Override
    public void setBoardId(String boardId) {
        boardDTO.setBoardId(boardId);
    }

    @Override
    public String getBoardId() {
        return boardDTO.getBoardId();
    }

    @Override
    public void setBoardName(String boardName) {
        boardDTO.setBoardName(boardName);
    }

    @Override
    public String getBoardName() {
        return boardDTO.getBoardName();
    }
}
