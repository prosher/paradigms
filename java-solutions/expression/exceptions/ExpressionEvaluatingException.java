package expression.exceptions;

public class ExpressionEvaluatingException extends RuntimeException {
    public ExpressionEvaluatingException(String message) {
        super(message);
    }
    public ExpressionEvaluatingException(String message, Throwable cause) {
        super(message, cause);
    }
}
