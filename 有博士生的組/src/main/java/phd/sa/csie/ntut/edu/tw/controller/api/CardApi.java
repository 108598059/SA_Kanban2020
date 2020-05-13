package phd.sa.csie.ntut.edu.tw.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CommitCardUsecase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;

@RestController
@RequestMapping("/api/card")
public class CardApi {
    @Autowired
    private CreateCardUseCase createCardUseCase;

    @Autowired
    private CommitCardUsecase commitCardUsecase;

    @PostMapping("/create")
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest body){
        CreateCardUseCaseInput createCardInput = new CreateCardUseCaseInput();

        createCardInput.setBoardID(body.getBoardID());
        createCardInput.setCardName(body.getCardName());

        CreateCardUseCaseOutput createCardOutput = new CreateCardUseCaseOutput();
        CreateCardResponse response = new CreateCardResponse();

        this.createCardUseCase.execute(createCardInput, createCardOutput);

        response.setCardId(createCardOutput.getCardId());
        response.setCardName(createCardOutput.getCardName());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

class CreateCardRequest {
    private String cardName;
    private String boardID;

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
    }
}

class CreateCardResponse {
    private String cardId;
    private String cardName;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
