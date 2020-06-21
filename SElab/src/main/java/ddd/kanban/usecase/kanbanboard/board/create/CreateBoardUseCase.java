package ddd.kanban.usecase.kanbanboard.board.create;


import ddd.kanban.domain.model.DomainEventBus;
import ddd.kanban.domain.model.kanbanboard.board.Board;
import ddd.kanban.usecase.kanbanboard.board.mapper.BoardEntityMapper;
import ddd.kanban.usecase.kanbanboard.board.entity.BoardEntity;
import ddd.kanban.usecase.repository.BoardRepository;

import java.util.UUID;

public class CreateBoardUseCase {

    private BoardRepository boardRepository;
    private DomainEventBus domainEventBus;

    public CreateBoardUseCase(BoardRepository boardRepository, DomainEventBus domainEventBus){
        this.boardRepository = boardRepository;
        this.domainEventBus = domainEventBus;
    }


    public void execute(CreateBoardInput createBoardInput, CreateBoardOutput createBoardOutput) {
        Board board = new Board(UUID.randomUUID().toString(), createBoardInput.getBoardTitle(), createBoardInput.getBoardDescription());
        BoardEntity boardEntity = BoardEntityMapper.mappingBoardEntityFrom(board);
        boardRepository.add(boardEntity);

        domainEventBus.postAll(board);

        createBoardOutput.setBoardId(board.getId());
        createBoardOutput.setBoardTitle(board.getTitle());
        createBoardOutput.setBoardDescription(board.getDescription());
    }
}
