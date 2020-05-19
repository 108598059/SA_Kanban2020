package domain.adapter.presenter;

import domain.adapter.BoardEntityDto;
import domain.adapter.view_model.CreateBoardViewModel;
import domain.usecase.board.create.CreateBoardUseCaseOutput;

public class CreateBoardUseCasePresenter implements Presenter<CreateBoardViewModel> {
    BoardEntityDto boardEntityDto;

    public CreateBoardUseCasePresenter(CreateBoardUseCaseOutput output) {
        boardEntityDto = new BoardEntityDto();
        boardEntityDto.setBoardId(output.getBoardId());
        boardEntityDto.setBoardName(output.getBoardName());
    }

    @Override
    public CreateBoardViewModel createView() {
        CreateBoardViewModel viewModel = new CreateBoardViewModel();
        viewModel.setViewModel(boardEntityDto);
        return viewModel;
    }
}
