package ddd.kanban.usecase.card.mapper;

import ddd.kanban.domain.model.card.Card;
import ddd.kanban.domain.model.card.Task;
import ddd.kanban.usecase.card.entity.CardEntity;
import ddd.kanban.usecase.card.entity.TaskEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CardEntityMapper {
    public static CardEntity mappingCardEntityFrom(Card card) {
        CardEntity cardEntity = new CardEntity(card.getId(), card.getTitle(), card.getBoardId(), card.getWorkflowId(), card.getLaneId());
        List<TaskEntity> taskEntityList = card.getTasks()
                                                .stream()
                                                .map(TaskEntityMapper::mappingTaskEntityFrom)
                                                .collect(Collectors.toList());
        cardEntity.setTaskEntities(taskEntityList);
        cardEntity.setDescription(card.getDescription());
        cardEntity.setCardTypeEntity(CardTypeMapper.mappingCardTypeEntityFrom(card.getCardType()));
        cardEntity.setTags(card.getTags());
        cardEntity.setAssignUsers(card.getAssignUsers());
        cardEntity.setPlannedStartDate(card.getPlannedStartDate());
        cardEntity.setPlannedFinishDate(card.getPlannedFinishDate());
        cardEntity.setPriority(card.getPriority());

        return cardEntity;
    }

    public static Card mappingCardFrom(CardEntity cardEntity) {
        List<Task> tasks =  cardEntity.getTaskEntities()
                                        .stream()
                                        .map(TaskEntityMapper::mappingTaskFrom)
                                        .collect(Collectors.toList());
        Card card = new Card(cardEntity.getId(), cardEntity.getTitle(), cardEntity.getBoardId(), cardEntity.getWorkflowId(), cardEntity.getLaneId(), tasks);
        card.setDescription(cardEntity.getDescription());
        card.setCardType(CardTypeMapper.mappingCardTypeFrom(cardEntity.getCardTypeEntity()));
        card.setTags(cardEntity.getTags());
        card.setAssignUsers(cardEntity.getAssignUsers());
        card.setPlannedStartDate(cardEntity.getPlannedStartDate());
        card.setPlannedFinishDate(cardEntity.getPlannedFinishDate());
        card.setPriority(cardEntity.getPriority());

        return card;
    }
}
