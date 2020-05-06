package phd.sa.csie.ntut.edu.tw.usecase.repository;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.domain.model.card.Card;

public interface CardRepository {

  public void add(Card card);

  public Card findCardByUUID(UUID uuid);

}