package phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardEnteredColumnEventRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryCardEnteredColumnEventRepository implements CardEnteredColumnEventRepository {
    private List<CardEnteredColumnEventDTO> cardEnteredColumnEventDTOList;

    public MemoryCardEnteredColumnEventRepository() {
        this.cardEnteredColumnEventDTOList = new ArrayList<>();
    }

    @Override
    public List<CardEnteredColumnEventDTO> findByCardID(String cardID) {
        List<CardEnteredColumnEventDTO> result = new ArrayList<>();
        for (CardEnteredColumnEventDTO dto: this.cardEnteredColumnEventDTOList) {
            if (dto.getCardID().equals(cardID)) {
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public void save(CardEnteredColumnEventDTO dto) {
        this.cardEnteredColumnEventDTOList.add(dto);
    }

    @Override
    public void update(CardEnteredColumnEventDTO dto) {
        this.save(dto);
    }

    @Override
    public CardEnteredColumnEventDTO findByID(String id) {
        for (CardEnteredColumnEventDTO dto: this.cardEnteredColumnEventDTOList) {
            if (dto.getSourceID().equals(id)) {
                return dto;
            }
        }
        throw new RuntimeException("Not found!");
    }
}
