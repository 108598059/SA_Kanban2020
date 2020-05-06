package domain.usecase.workflow;

import com.google.common.eventbus.Subscribe;
import domain.controller.CommitWorkflowInputImpl;
import domain.controller.CommitWorkflowOutputImpl;
import domain.entity.workflow.event.WorkflowCreated;
import domain.usecase.board.BoardRepository;
import domain.usecase.board.commit.CommitWorkflowInput;
import domain.usecase.board.commit.CommitWorkflowOutput;
import domain.usecase.board.commit.CommitWorkflowUseCase;

public class WorkflowEventHandler {

        private BoardRepository boardRepository;

        public WorkflowEventHandler(BoardRepository boardRepository) {
            this.boardRepository = boardRepository;
        }

        @Subscribe
        public void commitWorkflowHandleEvent(WorkflowCreated workflowCreated){

            CommitWorkflowInput commitWorkflowInput = new CommitWorkflowInputImpl();
            CommitWorkflowOutput commitWorkflowOutput = new CommitWorkflowOutputImpl();
            CommitWorkflowUseCase commitWorkflowUseCase = new CommitWorkflowUseCase(boardRepository);

            commitWorkflowInput.setBoardId(workflowCreated.getBoardId());
            commitWorkflowInput.setWorkflowId(workflowCreated.getWorkflowId());

            commitWorkflowUseCase.execute(commitWorkflowInput,commitWorkflowOutput);

        }
}

