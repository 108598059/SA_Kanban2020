package ddd.kanban.usecase.card.mapper;

import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.card.entity.TaskEntity;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TaskEntityMapperTest {
    @Test
    public void testMappingTaskEntityFrom(){
        Task task = new Task("0001", "test task entity from");
        TaskEntity taskEntity = TaskEntityMapper.mappingTaskEntityFrom(task);
        task.getDescription();
        assertEquals("0001", taskEntity.getId());
    }

    @Test
    public void testMappingTaskFrom(){
        TaskEntity taskEntity = TaskEntityMapper.mappingTaskEntityFrom(new Task("0001", "test task from"));
        Task task = TaskEntityMapper.mappingTaskFrom(taskEntity);
        assertEquals("0001", task.getId());
    }
}
