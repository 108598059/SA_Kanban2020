package tw.teddysoft.clean.domain.usecase.repository;

import java.util.List;

public class Repository<T> implements IRepository<T> {

    private final RepositoryPeer<T> repositoryPeer;

    public Repository(RepositoryPeer peer){
        repositoryPeer = peer;
    }

    public List<T> findAll(){
        return repositoryPeer.findAll();
    }

    public T findById(String id){
        return repositoryPeer.findById(id);
    }

    public T findFirstByName(String name){
        return repositoryPeer.findFirstByName(name);
    }

    public T save(T arg){
        return repositoryPeer.save(arg);
    }

    public boolean remove(T arg){
        return repositoryPeer.remove(arg);
    }
}
