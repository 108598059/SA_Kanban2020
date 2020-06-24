package phd.sa.csie.ntut.edu.tw.adapter.presenter.board.create;

import phd.sa.csie.ntut.edu.tw.adapter.view.model.board.create.CreateBoardViewModel;
import phd.sa.csie.ntut.edu.tw.usecase.board.create.CreateBoardUseCaseOutput;

public class CreateBoardPresenter implements CreateBoardUseCaseOutput {
    private String boardID;
    private String boardName;

    public CreateBoardViewModel build() {
        CreateBoardViewModel viewModel = new CreateBoardViewModel();
        viewModel.setBoardID(this.boardID);
        viewModel.setBoardName(this.boardName);
        return viewModel;
    }

    @Override
    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }

    @Override
    public String getBoardID() {
        return boardID;
    }

    @Override
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    @Override
    public String getBoardName() {
        return boardName;
    }
}