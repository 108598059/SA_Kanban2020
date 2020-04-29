package domain.usecase.card.create;

import domain.model.aggregate.DomainEventBus;
import domain.model.aggregate.card.Card;
import domain.model.aggregate.workflow.Workflow;
import domain.usecase.card.repository.ICardRepository;
import domain.usecase.workflow.repository.IWorkflowRepository;


public class CreateCardUseCase {
    private ICardRepository cardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public CreateCardUseCase(IWorkflowRepository workflowRepository, ICardRepository cardRepository, DomainEventBus eventBus){
        this.eventBus = eventBus;
        this.workflowRepository = workflowRepository;
        this.cardRepository = cardRepository;
    }
    public void execute(CreateCardUseCaseInput createCardUseCaseInput, CreateCardUseCaseOutput createCardUseCaseOutput) {
        Card card = new Card(createCardUseCaseInput.getCardName());
        Workflow workflow = workflowRepository.getWorkflowById(createCardUseCaseInput.getWorkflowId());
        workflow.addCardInLane(createCardUseCaseInput.getStageId(), card.getCardId());

        cardRepository.add(card);
//        workflowRepository.save(workflow);
        eventBus.postAll(card);

        createCardUseCaseOutput.setCardName(card.getCardName());
        createCardUseCaseOutput.setCardId(card.getCardId());
    }
}
