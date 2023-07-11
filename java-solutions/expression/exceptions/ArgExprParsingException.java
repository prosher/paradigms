package expression.exceptions;

public class ArgExprParsingException extends ExpressionParsingException {
    public ArgExprParsingException(String message) {
        super(message);
    }

    public ArgExprParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
