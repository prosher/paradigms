package expression.generic.base;

import expression.exceptions.*;
import expression.generic.calculators.Calculator;
import expression.generic.operations.*;

public class ExpressionParser<T> extends Parser implements TripleParser<T> {
    private final BinaryBuilder<T> binaryBuilder;
    private final Calculator<T> calculator;

    public ExpressionParser(final Calculator<T> calculator) {
        binaryBuilder = new BinaryBuilder<>(calculator);
        this.calculator = calculator;
    }

    @Override
    public TripleExpression<T> parse(final String expression) throws ExpressionParsingException {
        return parse(new StringSource(expression));
    }

    public TripleExpression<T> parse(final CharSource source) throws ExpressionParsingException {
        setSource(source);
        binaryBuilder.clear();
        skipWhitespace();
        TripleExpression<T> result = parseExpression();
        skipWhitespace();
        if (eof()) {
            return result;
        }
        throw new ExpressionParsingException("can't find source end");
    }

    private TripleExpression<T> parseExpression() throws ExpressionParsingException {
        binaryBuilder.addExpression(parseArgument());
        while (!test(')') && !test(END)) {
            skipWhitespace();
            binaryBuilder.addOperation(parseBinaryOperation());
            skipWhitespace();
            binaryBuilder.addExpression(parseArgument());
        }
        return binaryBuilder.getExpression();
    }

    private TripleExpression<T> parseArgument()
            throws ExpressionParsingException {
        if (take('(')) {
            binaryBuilder.beginParentheses();
            skipWhitespace();
            TripleExpression<T> result = parseExpression();
            skipWhitespace();
            expect(')');
            binaryBuilder.endParentheses();
            return result;
        } else if (between('0', '9')) {
            return parseConst("");
        } else if (take('-')) {
            if (between('1', '9')) {
                return parseConst("-");
            }
            return new Negate<>(calculator, parseArgument());
        } else {
            try {
                return handleToken();
            } catch (TokenExprParsingException e) {
                throw new ArgExprParsingException("argument not found "
                        + binaryBuilder.afterOperationError(), e);
            }
        }
    }

    private TripleExpression<T> handleToken()
            throws ExpressionParsingException {
        String token = parseToken();
        try {
            if (GenericMetadata.TRIPLE_EXPR_VAR_TOKENS.contains(token)) {
                return new Variable<>(token);
            } else if (token.equals("square")) {
                return new Square<>(calculator, parseArgument());
            } else if (token.equals("abs")) {
                return new Abs<>(calculator, parseArgument());
            } else {
                throw new VarExprParsingException("invalid variable name '" + token + "'");
            }
        } catch (ArgExprParsingException e) {
            throw new ArgExprParsingException(e.getMessage() + " after '" + token + "'");
        }
    }

    private String parseBinaryOperation() throws OpExprParsingException {
        if (take('+')) {
            return "+";
        } else if (take('-')) {
            return "-";
        } else if (take('*')) {
            return "*";
        } else if (take('/')) {
            return "/";
        } else {
            String token;
            try {
                token = parseToken();
            } catch (TokenExprParsingException e) {
                throw new OpExprParsingException("invalid operation", e);
            }
            if (token.equals("mod")) {
                return token;
            }
            throw new OpExprParsingException("unknown operation '" + token + "'");
        }
    }

    private static boolean isTokenStart(final char c) {
        return Character.isLetter(c) || c == '_';
    }

    private static boolean isTokenContent(final char c) {
        return isTokenStart(c) || Character.isDigit(c);
    }

    private String parseToken() throws TokenExprParsingException {
        if (isTokenStart(ch)) {
            final StringBuilder sb = new StringBuilder();
            while (isTokenContent(ch)) {
                sb.append(take());
            }
            return sb.toString();
        } else {
            throw new TokenExprParsingException("invalid token");
        }
    }

    private Const<T> parseConst(final String beginning) throws ConstExprParsingException {
        final StringBuilder sb = new StringBuilder(beginning);
        int value;
        try {
            takeInteger(sb);
            value = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new ConstExprParsingException("invalid const");
        }
        return new Const<>(calculator.castInt(value));
    }

    private void takeInteger(final StringBuilder sb) throws NumberFormatException {
        if (take('-')) {
            sb.append('-');
        }
        if (take('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            takeDigits(sb);
        } else {
            throw new NumberFormatException("invalid integer");
        }
    }

    private void takeDigits(final StringBuilder sb) {
        while (between('0', '9')) {
            sb.append(take());
        }
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }
}