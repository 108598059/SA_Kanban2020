package tw.teddysoft.clean.domain.usecase.repository;

import java.util.List;

public interface IRepository<T> {
    List<T> findAll();
    T findById(String id);
    T findFirstByName(String name);
    T save(T arg);
    boolean remove(T arg);
}
