package domain.usecase.card;

import com.google.common.eventbus.Subscribe;
import domain.entity.card.Card;
import domain.entity.card.event.CardCreated;
import domain.entity.workflow.Workflow;
import domain.usecase.workflow.WorkflowRepository;

public class CardEventHandler{

    private WorkflowRepository workflowRepository;

    public CardEventHandler(WorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void commitCardHandleEvent(CardCreated cardCreated){

        Workflow workflow = workflowRepository.getWorkFlowById(cardCreated.getWorkflowId());
        workflow.addCard(cardCreated.getStageId(), cardCreated.getSwimlaneId(), cardCreated.getCardId());

        workflowRepository.save(workflow);

    }
}
