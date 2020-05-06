package domain.controller;

import domain.usecase.board.commit.CommitWorkflowOutput;

public class CommitWorkflowOutputImpl implements CommitWorkflowOutput {

    private int numberOfWorkflow;
    public void setNumberOfWorkflow(int numberOfWorkflow) {
        this.numberOfWorkflow = numberOfWorkflow;
    }

    public int getNumberOfWorkflow() {
        return numberOfWorkflow;
    }
}
