package expression.exceptions;

public class TokenExprParsingException extends ExpressionParsingException {
    public TokenExprParsingException(String message) {
        super(message);
    }
    public TokenExprParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
