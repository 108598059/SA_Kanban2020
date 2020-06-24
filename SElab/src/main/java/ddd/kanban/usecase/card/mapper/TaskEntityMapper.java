package ddd.kanban.usecase.card.mapper;

import ddd.kanban.domain.model.card.card.Task;
import ddd.kanban.usecase.card.entity.TaskEntity;

public class TaskEntityMapper {
    public static TaskEntity mappingTaskEntityFrom(Task task) {
        TaskEntity taskEntity = new TaskEntity(task.getId(), task.getTitle(), task.getDescription(),
                                                task.getTags(), task.getAssignUsers(),
                                                task.getPlannedStartDate(), task.getPlannedFinishDate(), task.getHeader(),
                                                task.getPriority(), task.getExternalLink());
        return taskEntity;
    }

    public static Task mappingTaskFrom(TaskEntity taskEntity) {
        Task task = new Task(taskEntity.getId(), taskEntity.getTitle(), taskEntity.getDescription(),
                                taskEntity.getTags(), taskEntity.getAssignUsers(),
                                taskEntity.getPlannedStartDate(), taskEntity.getPlannedFinishDate(), taskEntity.getHeader(),
                                taskEntity.getPriority(), taskEntity.getExternalLink());
        return task;
    }
}
