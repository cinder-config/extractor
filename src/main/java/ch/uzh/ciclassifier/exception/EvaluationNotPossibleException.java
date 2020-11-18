package ch.uzh.ciclassifier.exception;

public class EvaluationNotPossibleException extends RuntimeException {
    private String message;

    public EvaluationNotPossibleException(String message) {
        this.message  = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
