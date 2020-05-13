package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTO;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardCreatedEventHandler {

  private CardRepository cardRepository;

	public CardCreatedEventHandler(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }
  
  @Subscribe
  public void execute(CardCreatedEvent e) {
    // TODO CardCreaded or CardUpdated?
    CardDTOConverter cardDTOConverter = new CardDTOConverter();
    CardDTO card = cardDTOConverter.toDTO(e.getEntity());
    this.cardRepository.update(card);
  }

}
