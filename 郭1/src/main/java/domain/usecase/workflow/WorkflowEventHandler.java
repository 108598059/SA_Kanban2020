package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.adapters.controller.workflow.input.CommitWorkflowInputImpl;
import domain.adapters.controller.workflow.output.CommitWorkflowOutputImpl;
import domain.entity.DomainEventBus;
import domain.entity.aggregate.workflow.event.WorkflowCreated;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.commit.CommitWorkflowInput;
import domain.usecase.board.commit.CommitWorkflowOutput;
import domain.usecase.board.commit.CommitWorkflowUseCase;

public class WorkflowEventHandler {

        private BoardRepository boardRepository;
        private DomainEventBus eventBus;

        public WorkflowEventHandler(BoardRepository boardRepository, DomainEventBus eventBus) {
            this.boardRepository = boardRepository;
            this.eventBus = eventBus;
        }

        @Subscribe
        public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){

            CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInputImpl();
            CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutputImpl();
            CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository, eventBus);

            commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
            commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

            commitWorkflowUseCase.execute(commitWorkflowInput,commitWorkflowOutput);

        }
}

