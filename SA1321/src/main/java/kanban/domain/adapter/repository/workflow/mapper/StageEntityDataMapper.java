package kanban.domain.adapter.repository.workflow.mapper;

import kanban.domain.adapter.repository.workflow.data.StageData;
import kanban.domain.usecase.stage.StageEntity;

public class StageEntityDataMapper {
    public static StageData transformEntityToData(StageEntity stageEntity) {
        StageData stageData = new StageData();

        stageData.setWorkflowId(stageEntity.getWorkflowId());
        stageData.setStageId(stageEntity.getStageId());
        stageData.setName(stageEntity.getName());
        stageData.setCardIds(stageEntity.getCardIds());

        return stageData;
    }
    public static StageEntity transformDataToEntity(StageData stageData) {
        StageEntity stageEntity = new StageEntity();

        stageEntity.setWorkflowId(stageData.getWorkflowId());
        stageEntity.setStageId(stageData.getStageId());
        stageEntity.setName(stageData.getName());
        stageEntity.setCardIds(stageData.getCardIds());

        return stageEntity;
    }
}
