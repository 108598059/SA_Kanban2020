package tw.teddysoft.clean.adapter.gateway.kanbanboard;


import tw.teddysoft.clean.domain.model.AggregateRoot;
import tw.teddysoft.clean.domain.model.DomainEvent;
import tw.teddysoft.clean.domain.usecase.repository.RepositoryPeer;

import java.util.ArrayList;
import java.util.List;

public class InMemoryDomainEventRepositoryPeer<T extends DomainEvent> implements RepositoryPeer<T> {

    private final List<T> entities;

    public InMemoryDomainEventRepositoryPeer(){
        entities = new ArrayList<T>();
    }

    @Override
    public List<T> findAll() {
        return entities;
    }

    @Override
    public T findById(String id) {
        for(T each : entities){
            if (each.getSourceId().equalsIgnoreCase(id))
                return each;
        }

        return null;
    }

    @Override
    public T findFirstByName(String name) {
        for(T each : entities){
            if (each.getSourceName().equals(name))
                return each;
        }
        throw new RuntimeException("Cannot find domain event with source name : " + name);
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


//        throw new RuntimeException("Domain event repository does not support findById query");
//        throw new RuntimeException("Domain event repository does not support findFirstByName query");
