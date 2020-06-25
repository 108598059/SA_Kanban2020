package tw.teddysoft.clean.domain.usecase.repository;

import java.util.List;

public interface RepositoryPeer<T> {

    public abstract List<T> findAll();
    public abstract T findById(String id);
    public abstract T findFirstByName(String name);
    public abstract T save(T arg);
    public abstract boolean remove(T arg);
}
