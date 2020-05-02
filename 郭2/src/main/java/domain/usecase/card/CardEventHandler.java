package domain.usecase.card;

import com.google.common.eventbus.Subscribe;
import domain.model.aggregate.card.event.CardCreated;
import domain.model.aggregate.workflow.Lane;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.workflow.repository.IWorkflowRepository;


public class CardEventHandler{

    private IWorkflowRepository workflowRepository;

    public CardEventHandler(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void commitCardHandleEvent(CardCreated cardCreated){

        Workflow workflow = workflowRepository.getWorkflowById(cardCreated.getWorkflowId());
        Lane lane = workflow.getLaneById(cardCreated.getLaneId());
        lane.addCardId(cardCreated.getCardId());
        workflow.addCardInLane(cardCreated.getLaneId(), cardCreated.getCardId());

        workflowRepository.save(workflow);
    }
}