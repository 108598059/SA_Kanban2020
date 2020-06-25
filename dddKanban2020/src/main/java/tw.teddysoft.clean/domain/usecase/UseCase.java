package tw.teddysoft.clean.domain.usecase;

public interface UseCase<I extends Input, O extends Output> {

    /**
     * Executes a business use case. Input and output object should be given
     * @param input a request for use case
     * @param output a response for use case
     */
    void execute(I input, O output);

    I createInput();

}