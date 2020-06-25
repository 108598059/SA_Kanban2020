package phd.sa.csie.ntut.edu.tw.usecase.repository.event;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.entered.CardEnteredColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.IRepository;

import java.util.Date;
import java.util.List;

public interface CardEnteredColumnEventRepository extends IRepository<CardEnteredColumnEventDTO> {
    List<CardEnteredColumnEventDTO> findByCardID(String cardID);
    List<CardEnteredColumnEventDTO> findByCardIDInPeriod(String cardID, Date startTime, Date endTime);
}
