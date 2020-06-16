package phd.sa.csie.ntut.edu.tw.usecase.repository.event;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.move.dto.left.CardLeftColumnEventDTO;
import phd.sa.csie.ntut.edu.tw.usecase.repository.IRepository;

import java.util.List;

public interface CardLeftColumnEventRepository extends IRepository<CardLeftColumnEventDTO> {
    List<CardLeftColumnEventDTO> findByCardID(String cardID);
}
