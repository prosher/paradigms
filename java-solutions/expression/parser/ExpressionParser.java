package expression.parser;

import expression.*;

public class ExpressionParser extends Parser implements TripleParser {
    private final ExpressionBuilder exprBuilder;

    public ExpressionParser() {
        exprBuilder = new StackExpressionBuilder();
    }

    @Override
    public TripleExpression parse(final String expression) {
        return parse(new StringSource(expression));
    }

    public TripleExpression parse(final CharSource source) {
        setSource(source);
        exprBuilder.clear();

        TripleExpression result = parseExpression();
        if (eof()) {
            return result;
        }
        throw error("can't find source end");
    }

    /*
     *   Expression
     *       ws Unit ws [BinaryOperation ws Unit ws]
     * */

    private TripleExpression parseExpression() {
        skipWhitespace();
        exprBuilder.addExpression(Evaluateable.toEvaluateable(parseUnit()));
        skipWhitespace();
        while (!test(')') && !test(END)) {
            exprBuilder.addOperation(parseBinaryOperation());
            skipWhitespace();
            exprBuilder.addExpression(Evaluateable.toEvaluateable(parseUnit()));
            skipWhitespace();
        }
        return exprBuilder.getExpression();
    }

    /*
     *   Unit
     *       LP Expression RP
     *       Const
     *       UnaryOperation ws Unit
     *       Token
     *           Variable
     *           UnaryOperation
     * */
    private TripleExpression parseUnit() {
        skipWhitespace();
        if (take('(')) {
            exprBuilder.beginParentheses();
            TripleExpression result = parseExpression();
            expect(')');
            exprBuilder.endParentheses();
            return result;
        } else if (between('0', '9')) {
            return parseConst("");
        } else if (take('-')) {
            if (between('1', '9')) {
                return parseConst("-");
            }
            return new Negate(Evaluateable.toEvaluateable(parseUnit()));
        } else {
            String token = parseToken();
            if (Metadata.TRIPLE_EXPR_VAR_TOKENS.contains(token)) {
                return new Variable(token);
            } else if (token.equals("count")) {
                return new Count(Evaluateable.toEvaluateable(parseUnit()));
            } else {
                throw error("invalid variable name");
            }
        }
    }

    private Operation parseBinaryOperation() {
        if (take('+')) {
            return new Add(null, null);
        } else if (take('-')) {
            return new Subtract(null, null);
        } else if (take('*')) {
            return new Multiply(null, null);
        } else if (take('/')) {
            return new Divide(null, null);
        } else {
            final String token = parseToken();
            if (token.equals("set")) {
                return new Set(null, null);
            } else if (token.equals("clear")) {
                return new Clear(null, null);
            }
            throw error("invalid operation");
        }
    }

    private static boolean isTokenStart(final char c) {
        return Character.isLetter(c) || c == '_';
    }

    private static boolean isTokenContent(final char c) {
        return isTokenStart(c) || Character.isDigit(c);
    }

    private String parseToken() {
        if (isTokenStart(ch)) {
            final StringBuilder sb = new StringBuilder();
            while (isTokenContent(ch)) {
                sb.append(take());
            }
            return sb.toString();
        } else {
            throw error("invalid token");
        }
    }

    private Const parseConst(final String beginning) {
        final StringBuilder sb = new StringBuilder(beginning);
        int value;
        try {
            takeInteger(sb);
            value = Integer.parseInt(sb.toString());
        } catch (IllegalArgumentException e) {
            throw error("invalid const");
        }
        return new Const(value);
    }

    private void takeInteger(final StringBuilder sb) {
        if (take('-')) {
            sb.append('-');
        }
        if (take('0')) {
            sb.append('0');
        } else if (between('1', '9')) {
            takeDigits(sb);
        } else {
            throw error("invalid integer");
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