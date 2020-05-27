package phd.sa.csie.ntut.edu.tw.adapter.repository.memory;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.CardLeftColumnEventRepository;

import java.util.ArrayList;
import java.util.List;

public class MemoryCardLeftColumnEventRepository implements CardLeftColumnEventRepository {
    private List<CardLeftColumnEventDTO> cardLeftColumnEventDTOList;

    public MemoryCardLeftColumnEventRepository() {
        this.cardLeftColumnEventDTOList = new ArrayList<>();
    }

    @Override
    public List<CardLeftColumnEventDTO> findByCardID(String cardID) {
        List<CardLeftColumnEventDTO> result = new ArrayList<>();
        for (CardLeftColumnEventDTO dto: this.cardLeftColumnEventDTOList) {
            if (dto.getCardID().equals(cardID)) {
                result.add(dto);
            }
        }
        return result;
    }

    @Override
    public void save(CardLeftColumnEventDTO dto) {
        this.cardLeftColumnEventDTOList.add(dto);
    }

    @Override
    public void update(CardLeftColumnEventDTO dto) {
        this.save(dto);
    }

    @Override
    public CardLeftColumnEventDTO findByID(String id) {
        for (CardLeftColumnEventDTO dto: this.cardLeftColumnEventDTOList) {
            if (dto.getSourceID().equals(id)) {
                return dto;
            }
        }
        throw new RuntimeException("Not found!");
    }
}
