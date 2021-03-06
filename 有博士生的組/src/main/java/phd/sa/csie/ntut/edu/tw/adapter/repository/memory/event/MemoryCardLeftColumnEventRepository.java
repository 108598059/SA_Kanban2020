package phd.sa.csie.ntut.edu.tw.adapter.repository.memory.event;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.event.CardLeftColumnEventRepository;

import java.util.ArrayList;
import java.util.Date;
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
    public List<CardLeftColumnEventDTO> findByCardIDInPeriod(String cardID, Date startTime, Date endTime) {
        List<CardLeftColumnEventDTO> cardLeftColumnEventDTOList = this.findByCardID(cardID);
        List<CardLeftColumnEventDTO> result = new ArrayList<>();
        for (CardLeftColumnEventDTO dto: cardLeftColumnEventDTOList) {
            if (dto.getOccurredTime().compareTo(startTime) >= 0 && dto.getOccurredTime().compareTo(endTime) <= 0) {
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
