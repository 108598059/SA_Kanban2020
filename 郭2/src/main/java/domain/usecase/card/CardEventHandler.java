package domain.usecase.card;

import com.google.common.eventbus.Subscribe;
import domain.model.aggregate.card.event.CardCreated;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.card.commit.CommitCardUseCase;
import domain.usecase.card.commit.CommitCardUseCaseInput;
import domain.usecase.card.commit.CommitCardUseCaseOutput;
import domain.usecase.workflow.repository.IWorkflowRepository;


public class CardEventHandler{

    private IWorkflowRepository workflowRepository;

    public CardEventHandler(IWorkflowRepository workflowRepository) {
        this.workflowRepository = workflowRepository;
    }

    @Subscribe
    public void createCardHandleEvent(CardCreated cardCreated){
        CommitCardUseCase commitCardUseCase = new CommitCardUseCase(workflowRepository);
        CommitCardUseCaseInput commitCardUseCaseInput = new CommitCardUseCaseInput();
        CommitCardUseCaseOutput commitCardUseCaseOutput = new CommitCardUseCaseOutput();

        commitCardUseCaseInput.setCardId(cardCreated.getCardId());
        commitCardUseCaseInput.setLaneId(cardCreated.getLaneId());
        commitCardUseCaseInput.setWorkflowId(cardCreated.getWorkflowId());

        commitCardUseCase.execute(commitCardUseCaseInput, commitCardUseCaseOutput);
    }
}