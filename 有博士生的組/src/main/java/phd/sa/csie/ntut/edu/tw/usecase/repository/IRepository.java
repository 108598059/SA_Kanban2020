package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public interface IRepository<T extends DTO> {
    void save(T dto);
    void update(T dto);
    T findByID(String id);
}