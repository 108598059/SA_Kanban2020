package phd.sa.csie.ntut.edu.tw.usecase.card.create;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.card.event.CardCreatedEvent;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

public class CardCreatedEventHandler {

  private CardRepository cardRepository;

	public CardCreatedEventHandler(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }
  
  @Subscribe
  public void execute(CardCreatedEvent e) {
    // TODO CardCreaded or CardUpdated?
    this.cardRepository.save(e.getEntity());
  }

}
