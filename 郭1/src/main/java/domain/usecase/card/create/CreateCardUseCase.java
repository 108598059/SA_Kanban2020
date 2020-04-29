package domain.usecase.card.create;

import domain.entity.DomainEventBus;
import domain.entity.card.Card;
import domain.usecase.card.CardRepository;

public class CreateCardUseCase {
    private CardRepository cardRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(CardRepository cardRepository, DomainEventBus eventBus){
        this.cardRepository = cardRepository;
        this.eventBus = eventBus;
    }

    public void execute(CreateCardInput input, CreateCardOutput output ) {

        Card newCard = new Card(input.getWorkflowId(),input.getStageId(),input.getSwimlaneId()) ;
        newCard.setName( input.getCardName() ) ;

        cardRepository.add( newCard ) ;

        eventBus.postAll(newCard);

        output.setCardId( newCard.getId()) ;


    }


}
