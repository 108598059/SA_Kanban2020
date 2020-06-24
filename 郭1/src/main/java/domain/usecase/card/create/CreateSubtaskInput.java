package domain.usecase.card.create;

public interface CreateSubtaskInput {
    public void setSubtaskName(String name ) ;
    public String getSubtaskName() ;

    void setCardId(String cardId);
    public String getCardId();
}
