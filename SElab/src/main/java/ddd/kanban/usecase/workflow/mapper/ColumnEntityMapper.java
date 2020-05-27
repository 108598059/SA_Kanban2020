package ddd.kanban.usecase.workflow.mapper;

import ddd.kanban.domain.model.workflow.Column;
import ddd.kanban.domain.model.workflow.Lane;
import ddd.kanban.usecase.workflow.entity.ColumnEntity;

public class ColumnEntityMapper {

    public static Lane mappingColumnFrom(ColumnEntity columnEntity){
        Lane column = new Column(columnEntity.getId(), columnEntity.getTitle(), columnEntity.getWorkflowId());
        column.setCommittedCards(columnEntity.getCommittedCards());
        return column;
    }

    public static ColumnEntity mappingColumnEntityFrom(Lane column){
        ColumnEntity columnEntity = new ColumnEntity(column.getId(), column.getTitle(), column.getWorkflowId());
        columnEntity.setCommittedCards(column.getCommittedCards());
        return columnEntity;
    }
}
