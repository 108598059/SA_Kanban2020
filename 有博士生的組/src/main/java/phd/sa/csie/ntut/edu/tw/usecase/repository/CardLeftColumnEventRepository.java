package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardLeftColumnEventDTO;

import java.util.List;

public interface CardLeftColumnEventRepository extends IRepository<CardLeftColumnEventDTO> {
    public List<CardLeftColumnEventDTO> findByCardID(String cardID);
}
