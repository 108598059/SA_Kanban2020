package domain.usecase.workflow.commit;

import domain.entity.workflow.Workflow;

public interface CommitCardOutput {
    String getCardId();

    void setCardId(String cardId);
}
