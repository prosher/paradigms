package expression.exceptions;

public class OpExprParsingException extends ExpressionParsingException {
    public OpExprParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public OpExprParsingException(String message) {
        super(message);
    }
}
