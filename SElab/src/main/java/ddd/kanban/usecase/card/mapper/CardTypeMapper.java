package ddd.kanban.usecase.card.mapper;

import ddd.kanban.domain.model.card.CardType;
import ddd.kanban.usecase.card.entity.CardTypeEntity;

public class CardTypeMapper {
    public static CardType mappingCardTypeFrom(CardTypeEntity cardTypeEntity){
        return new CardType();
    }

    public static CardTypeEntity mappingCardTypeEntityFrom(CardType cardType){
        return new CardTypeEntity();
    }
}
