package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.usecase.event.handler.sourcing.dto.CardEnteredColumnEventDTO;

import java.util.List;

public interface CardEnteredColumnEventRepository extends IRepository<CardEnteredColumnEventDTO> {
    public List<CardEnteredColumnEventDTO> findByCardID(String cardID);
}
