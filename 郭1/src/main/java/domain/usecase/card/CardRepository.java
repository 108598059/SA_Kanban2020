package domain.usecase.card;

public interface CardRepository {
    void save(CardDTO cardDTO);
    void add(CardDTO cardDTO);
    CardDTO getCardById(String id);
}
