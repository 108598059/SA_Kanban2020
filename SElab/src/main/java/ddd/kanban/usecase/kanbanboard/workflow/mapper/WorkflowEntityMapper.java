package ddd.kanban.usecase.kanbanboard.workflow.mapper;

import ddd.kanban.domain.model.kanbanboard.workflow.Column;
import ddd.kanban.domain.model.kanbanboard.workflow.Workflow;
import ddd.kanban.usecase.kanbanboard.workflow.entity.ColumnEntity;
import ddd.kanban.usecase.kanbanboard.workflow.entity.WorkflowEntity;

import java.util.List;
import java.util.stream.Collectors;

public class WorkflowEntityMapper {
    public static Workflow mappingWorkflowFrom(WorkflowEntity workflowEntity){

        List<Column> columns = workflowEntity.getColumnEntities()
                                    .stream()
                                    .map(ColumnEntityMapper::mappingColumnFrom)
                                    .collect(Collectors.toList());
        return new Workflow(workflowEntity.getId(), workflowEntity.getTitle(), workflowEntity.getBoardId(), columns);
    }

    public static WorkflowEntity mappingWorkflowEntityFrom(Workflow workflow){
        WorkflowEntity workflowEntity = new WorkflowEntity(workflow.getId(), workflow.getTitle(), workflow.getBoardId());
        List<ColumnEntity> columnEntities = workflow.getColumns()
                                            .stream()
                                            .map(ColumnEntityMapper::mappingColumnEntityFrom)
                                            .collect(Collectors.toList());
        workflowEntity.setColumnEntities(columnEntities);
        return workflowEntity;
    }
}
