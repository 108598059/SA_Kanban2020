package tw.teddysoft.clean.adapter.gateway.kanbanboard;


import tw.teddysoft.clean.domain.model.AggregateRoot;
import tw.teddysoft.clean.domain.model.Entity;
import tw.teddysoft.clean.domain.usecase.repository.RepositoryPeer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryAggregateRootRepositoryPeer<T extends AggregateRoot> implements RepositoryPeer<T> {

    private final List<T> entities;

    public InMemoryAggregateRootRepositoryPeer(){
        entities = new ArrayList<T>();
    }

    @Override
    public List<T> findAll() {
        return entities;
    }

    @Override
    public T findById(String id) {
        for(T each : entities){
            if (each.getId().equalsIgnoreCase(id))
                return each;
        }

        return null;

//        throw new RuntimeException("Cannot find object with id : " + id);
    }

    @Override
    public T findFirstByName(String name) {
        for(T each : entities){
            if (each.getName().equals(name))
                return each;
        }
        throw new RuntimeException("Cannot find object with name : " + name);
    }

    @Override
    public T save(T arg) {
        if (entities.contains(arg))
            return arg;
        else if (entities.add(arg))
            return arg;
        else
            return null;
    }

    @Override
    public boolean remove(T arg) {
        return entities.remove(arg);
    }

}
