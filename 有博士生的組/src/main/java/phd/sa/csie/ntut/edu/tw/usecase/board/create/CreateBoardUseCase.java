package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phd.sa.csie.ntut.edu.tw.domain.model.DomainEventBus;
import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

@Service
public class CreateBoardUseCase extends UseCase<CreateBoardUseCaseInput, CreateBoardUseCaseOutput> {
  private BoardRepository boardRepository;
  private BoardDTOConverter boardDTOConverter;

  public CreateBoardUseCase(@Autowired BoardRepository boardRepository, @Autowired DomainEventBus eventBus, @Autowired BoardDTOConverter boardDTOConverter) {
    super(eventBus);

    this.boardRepository = boardRepository;
    this.boardDTOConverter = boardDTOConverter;
  }

  public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
    String title = input.getBoardName();

    Board board = new Board(title);
    BoardDTO boardDTO = this.boardDTOConverter.toDTO(board);
    this.boardRepository.save(boardDTO);

    output.setBoardId(board.getId().toString());
    output.setBoardName(board.getName());
  }
}