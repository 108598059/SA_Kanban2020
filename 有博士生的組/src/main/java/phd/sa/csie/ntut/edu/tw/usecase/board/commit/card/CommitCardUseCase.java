package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CommitCardUseCase {
  private CardRepository cardRepository;
  private BoardRepository boardRepository;

  public CommitCardUseCase(CardRepository cardRepository, BoardRepository boardRepository) {
    this.cardRepository = cardRepository;
    this.boardRepository = boardRepository;
  }
  
  public void execute(CommitCardUseCaseInput input, CommitCardUseCaseOutput output) {
    Board board = BoardDTOConverter.toEntity(this.boardRepository.findByID(input.getBoardID()));
    Card card = CardDTOConverter.toEntity(this.cardRepository.findByID(input.getCardID()));

    board.commitCard(card);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));

    output.setBoardID(board.getID().toString());
    output.setCardID(card.getID().toString());
  }
}
