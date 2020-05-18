package phd.sa.csie.ntut.edu.tw.usecase.board.commit.card;

import com.google.common.eventbus.Subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import phd.sa.csie.ntut.edu.tw.model.board.Board;
import phd.sa.csie.ntut.edu.tw.model.card.Card;
import phd.sa.csie.ntut.edu.tw.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
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
  
  public void execute(CommitCardInput input, CommitCardOutput output) {
    Board board = BoardDTOConverter.toEntity(this.boardRepository.findById(input.getBoardID()));
    Card card = CardDTOConverter.toEntity(this.cardRepository.findById(input.getCardID()));

    board.commitCard(card);

    this.boardRepository.update(BoardDTOConverter.toDTO(board));

    output.setBoardID(board.getId().toString());
    output.setCardID(card.getId().toString());
  }
}
