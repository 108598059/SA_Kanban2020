package phd.sa.csie.ntut.edu.tw.usecase.repository;

import java.util.UUID;

import phd.sa.csie.ntut.edu.tw.usecase.dto.DTO;

public interface IRepository<T extends DTO> {
    public void save(T dto);

    public T findById(String id);
}