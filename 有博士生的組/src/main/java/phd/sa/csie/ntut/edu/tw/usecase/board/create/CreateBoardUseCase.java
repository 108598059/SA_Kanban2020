package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

public class CreateBoardUseCase extends UseCase<BoardRepository, BoardDTOConverter, CreateBoardUseCaseInput, CreateBoardUseCaseOutput> {

  public CreateBoardUseCase(BoardRepository boardRepository, DomainEventBus eventBus, BoardDTOConverter boardDTOConverter) {
    super(boardRepository, eventBus, boardDTOConverter);
  }

  public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
    String title = input.getTitle();

    Board board = new Board(title);
    BoardDTO boardDTO = this.dtoConverter.toDTO(board);
    this.repository.save(boardDTO);

    output.setBoardId(board.getId().toString());
    output.setBoardTitle(board.getName());
  }
}