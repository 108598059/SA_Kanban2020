package ddd.kanban.adapter.presenter.board.get;

import ddd.kanban.adapter.presenter.board.viewmodel.GetAllBoardsViewModel;
import ddd.kanban.usecase.board.dto.BoardDTO;
import ddd.kanban.usecase.board.get.GetAllBoardsOutput;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllBoardsPresenter implements GetAllBoardsOutput {
    private List<BoardDTO> boards;


    @Override
    public List<BoardDTO> getBoards() {
        return boards;
    }

    @Override
    public void setBoards(List<BoardDTO> boards) {
        this.boards = boards;
    }

    public List<GetAllBoardsViewModel> buildGetAllBoardsBoardViewModel(){
        return boards.stream()
                .map(this::mappingBoardDTOToGetAllBoardsBoardViewModel)
                .collect(Collectors.toList());
    }

    private GetAllBoardsViewModel mappingBoardDTOToGetAllBoardsBoardViewModel(BoardDTO boardDTO){
        GetAllBoardsViewModel getAllBoardsViewModel = new GetAllBoardsViewModel();
        getAllBoardsViewModel.setBoardId(boardDTO.getId());
        getAllBoardsViewModel.setBoardTitle(boardDTO.getTitle());
        getAllBoardsViewModel.setBoardDescription(boardDTO.getDescription());
        return getAllBoardsViewModel;
    }

}
