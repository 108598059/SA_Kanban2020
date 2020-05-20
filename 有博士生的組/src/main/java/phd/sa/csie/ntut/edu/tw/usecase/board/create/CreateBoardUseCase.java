package phd.sa.csie.ntut.edu.tw.usecase.board.create;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.usecase.UseCase;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;

import java.util.UUID;

public class CreateBoardUseCase extends UseCase<CreateBoardUseCaseInput, CreateBoardUseCaseOutput> {
  private BoardRepository boardRepository;

  public CreateBoardUseCase(BoardRepository boardRepository) {
    this.boardRepository = boardRepository;
  }

  public void execute(CreateBoardUseCaseInput input, CreateBoardUseCaseOutput output) {
    Board board = new Board(UUID.fromString(input.getWorkspaceID()), input.getBoardName());

    this.boardRepository.save(BoardDTOConverter.toDTO(board));

    output.setBoardName(board.getName());
    output.setBoardID(board.getID().toString());
  }
}