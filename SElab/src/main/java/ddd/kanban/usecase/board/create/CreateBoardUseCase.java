package ddd.kanban.usecase.board.create;


import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.board.Board;
import ddd.kanban.usecase.DTO.BoardDTO;
import ddd.kanban.usecase.DTOMapper;
import ddd.kanban.usecase.repository.BoardRepository;

import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;
    private DTOMapper dtoMapper;

    public CreateBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus){
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
        dtoMapper = new DTOMapper();
    }


    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {
        Board board = new Board(UUID.randomUUID().toString(), createBoardInput.getBoardTitle(), createBoardInput.getBoardDescription());
        BoardDTO boardDTO = dtoMapper.mappingBoardDTOFrom(board);
        boardRepository.add(boardDTO);

        domainEventBus.postAll(board);

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardTitle(board.getTitle());
        createBoardOutput.setBoardDescription(board.getDescription());
    }
}
