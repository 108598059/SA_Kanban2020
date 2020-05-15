package phd.sa.csie.ntut.edu.tw.usecase;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.board.Board;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.board.dto.BoardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.BoardRepository;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class DomainEventHandler {
  private CardRepository cardRepository;
  private BoardRepository boardRepository;

  public DomainEventHandler(CardRepository cardRepository, BoardRepository boardRepository) {
    this.cardRepository = cardRepository;
    this.boardRepository = boardRepository;
  }
  
  @Subscribe
  public void execute(CardCreatedEvent e) {
    Card card = e.getEntity();
    Board board = BoardDTOConverter.toEntity(this.boardRepository.findById(card.getBoardId().toString()));

    board.commitCard(card);
    this.cardRepository.save(CardDTOConverter.toDTO(card));

    BoardDTO boardDTO = BoardDTOConverter.toDTO(board);
    this.boardRepository.update(boardDTO);
  }
}
