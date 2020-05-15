package phd.sa.csie.ntut.edu.tw.usecase.repository;

import phd.sa.csie.ntut.edu.tw.usecase.DTO;

public interface IRepository<T extends DTO> {
    public void save(T dto);
    public void update(T dto);
    public T findById(String id);
}