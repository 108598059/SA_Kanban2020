package ddd.kanban.adapter.presenter.kanbanboard.board.create;

import ddd.kanban.adapter.presenter.kanbanboard.board.viewmodel.CreateBoardViewModel;
import ddd.kanban.usecase.kanbanboard.board.create.CreateBoardOutput;

public class CreateBoardPresenter implements CreateBoardOutput {
    private String boardId;
    private String boardTitle;
    private String boardDescription;

    @Override
    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    @Override
    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }

    @Override
    public String getBoardId() {
        return this.boardId;
    }

    @Override
    public String getBoardTitle() {
        return this.boardTitle;
    }

    @Override
    public String getBoardDescription() {
        return this.boardDescription;
    }

    public CreateBoardViewModel buildCreateBoardViewModel() {
        CreateBoardViewModel createBoardViewModel = new CreateBoardViewModel();
        createBoardViewModel.setBoardId(this.boardId);
        createBoardViewModel.setBoardTitle(this.boardTitle);
        createBoardViewModel.setBoardDescription(this.boardDescription);
        return createBoardViewModel;
    }
}
