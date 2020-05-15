package phd.sa.csie.ntut.edu.tw.usecase.card.edit;

import com.google.common.eventbus.Subscribe;

import phd.sa.csie.ntut.edu.tw.domain.model.board.event.CardEnterColumnEvent;
import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;
import phd.sa.csie.ntut.edu.tw.usecase.card.dto.CardDTOConverter;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardRepository;

import java.util.UUID;

public class EditCardColumnUsecase {
    private CardRepository cardRepository;

    public EditCardColumnUsecase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Subscribe
    public void execute(CardEnterColumnEvent e) {
        Card card = CardDTOConverter.toEntity(this.cardRepository.findById(e.getCardId()));

        card.setColumnId(UUID.fromString(e.getColumnId()));

        this.cardRepository.update(CardDTOConverter.toDTO(card));
    }
}
