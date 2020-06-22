package ddd.kanban.usecase.kanbanboard.board.get;

import ddd.kanban.usecase.kanbanboard.board.mapper.BoardDTOMapper;
import ddd.kanban.usecase.kanbanboard.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.kanbanboard.board.dto.BoardDTO;
import ddd.kanban.usecase.repository.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetAllBoardsUseCase {
    private BoardRepository boardRepository;

    public GetAllBoardsUseCase(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }


    public void execute(GetAllBoardsInput getAllBoardsInput, GetAllBoardsOutput getAllBoardsOutput) {
        List<BoardDTO> boardDTOS = boardRepository.findAll().stream()
                                                            .map(BoardEntityMapper::mappingBoardFrom)
                                                            .map(BoardDTOMapper::mappingBoardDTOFrom)
                                                            .collect(Collectors.toList());
        getAllBoardsOutput.setBoards(boardDTOS);
    }

}
