package kanban.domain.usecase.stage.mapper;


import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.usecase.stage.StageEntity;

public class StageEntityModelMapper {
    public static Stage transformEntityToModel(StageEntity stageEntity) {
        Stage stage = new Stage();

        stage.setWorkflowId(stageEntity.getWorkflowId());
        stage.setStageId(stageEntity.getStageId());
        stage.setName(stageEntity.getName());
        stage.setCardIds(stageEntity.getCardIds());

        return stage;
    }
    public static StageEntity transformModelToEntity(Stage stage) {
        StageEntity stageEntity = new StageEntity();

        stageEntity.setWorkflowId(stage.getWorkflowId());
        stageEntity.setStageId(stage.getStageId());
        stageEntity.setName(stage.getName());
        stageEntity.setCardIds(stage.getCardIds());

        return stageEntity;
    }
}
