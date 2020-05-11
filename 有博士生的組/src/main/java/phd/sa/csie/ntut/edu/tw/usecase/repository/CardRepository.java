package phd.sa.csie.ntut.edu.tw.usecase.repository;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public interface CardRepository {

  public void add(DTO entity);

  public DTO findById(UUID uuid);

  public void save(DTO entity);

}