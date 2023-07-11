package expression.exceptions;

public class VarExprParsingException extends TokenExprParsingException {
    public VarExprParsingException(String message) {
        super(message);
    }
    public VarExprParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
