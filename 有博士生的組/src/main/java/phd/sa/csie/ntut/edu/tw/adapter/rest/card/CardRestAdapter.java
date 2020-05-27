package phd.sa.csie.ntut.edu.tw.adapter.rest.card;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCase;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseInput;
import phd.sa.csie.ntut.edu.tw.usecase.card.create.CreateCardUseCaseOutput;

@RestController
@RequestMapping("/api/card")
public class CardRestAdapter {
    @Autowired
    private CreateCardUseCase createCardUseCase;

    @PostMapping("/create")
    public ResponseEntity<CreateCardResponse> createCard(@RequestBody CreateCardRequest requestBody){
        CreateCardUseCaseInput createCardInput = new CreateCardUseCaseInput();
        CreateCardUseCaseOutput createCardOutput = new CreateCardUseCaseOutput();

        createCardInput.setBoardID(requestBody.getBoardID());
        createCardInput.setCardName(requestBody.getCardName());

        try {
            this.createCardUseCase.execute(createCardInput, createCardOutput);

            CreateCardResponseOK responseBody = new CreateCardResponseOK();
            responseBody.setCardID(createCardOutput.getCardID());
            responseBody.setCardName(createCardOutput.getCardName());

            return ResponseEntity.status(HttpStatus.OK).body(responseBody);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CreateCardResponseBadRequest(e.getMessage()));
        }
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

class CreateCardPresenter {

}

abstract class CreateCardResponse {

}

class CreateCardResponseOK extends CreateCardResponse {
    private String cardID;
    private String cardName;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}

class CreateCardResponseBadRequest extends CreateCardResponse {
    private String errorMsg;

    public CreateCardResponseBadRequest(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
