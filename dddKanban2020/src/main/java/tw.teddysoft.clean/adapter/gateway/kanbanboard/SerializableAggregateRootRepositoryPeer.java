package tw.teddysoft.clean.adapter.gateway.kanbanboard;


import tw.teddysoft.clean.domain.model.AggregateRoot;
import tw.teddysoft.clean.domain.usecase.repository.RepositoryPeer;

import java.util.ArrayList;
import java.util.List;

public class SerializableAggregateRootRepositoryPeer<T extends AggregateRoot> implements RepositoryPeer<T> {

//    private final String STORED_FILE_NAME = "board-repository.ser";
    private final List<T> boards;
    private final String fileName;

    public SerializableAggregateRootRepositoryPeer(String fileName){

        this.fileName = fileName;
        boards = new ArrayList<T>();
        if (SerializationUtil.storedFileExists(fileName)){
            boards.addAll((List<T>) SerializationUtil.loadFromFile(fileName));
        }
    }

    public List<T> findAll() {
        return boards;
    }

    @Override
    public T findById(String id) {
        for(T each : boards){
            if (each.getId().equalsIgnoreCase(id))
                return each;
        }
        throw new RuntimeException("Cannot find object with id : " + id);
    }

    @Override
    public T findFirstByName(String name) {
        for(T each : boards){
            if (each.getName().equals(name))
                return each;
        }
        throw new RuntimeException("Cannot find object with name : " + name);
    }

    @Override
    public T save(T arg) {
        if (boards.contains(arg)){
            SerializationUtil.saveToFile(fileName, boards);
            return arg;
        }
        else if (boards.add(arg)){
            SerializationUtil.saveToFile(fileName, boards);
            return arg;
        }
        else
            return null;
    }

    @Override
    public boolean remove(T arg) {
        boolean result =  boards.remove(arg);
        SerializationUtil.saveToFile(fileName, boards);
        return result;
    }

}
