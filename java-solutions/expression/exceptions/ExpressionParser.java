package expression.exceptions;

import expression.*;
import expression.parser.Clear;
import expression.parser.Count;
import expression.parser.Set;

public class ExpressionParser extends Parser implements TripleParser {
    private final ExpressionBuilder exprBuilder;

    public ExpressionParser() {
        exprBuilder = new StackExpressionBuilder();
    }

    @Override
    public TripleExpression parse(final String expression) throws ExpressionParsingException {
        return parse(new StringSource(expression));
    }

    public TripleExpression parse(final CharSource source) throws ExpressionParsingException {
        setSource(source);
        exprBuilder.clear();
        TripleExpression result = parseExpression();
        if (eof()) {
            return result;
        }
        throw new ExpressionParsingException("can't find source end");
    }

    /*
     *   Expression
     *       ws Argument ws [BinaryOperation ws Argument ws]
     * */

    private TripleExpression parseExpression() throws ExpressionParsingException {
        skipWhitespace();
//        try {
            exprBuilder.addExpression(Evaluateable.toEvaluateable(parseArgument()));
//        } catch (ArgExprParsingException e) {
//            throw new ArgExprParsingException("initial " + e.getMessage());
//        }
        skipWhitespace();
        while (!test(')') && !test(END)) {
            exprBuilder.addOperation(parseBinaryOperation());
            skipWhitespace();
//            try {
                exprBuilder.addExpression(Evaluateable.toEvaluateable(parseArgument()));
//            } catch (ArgExprParsingException e) {
//                throw new ArgExprParsingException(e.getMessage() + " after operation ")
//            }
            skipWhitespace();
        }
        return exprBuilder.getExpression();
    }

    /*
    *   Argument
    *       LP Expression RP
    *       Const
    *       UnaryOperation ws Argument
    *       StringToken
    *           Variable
    *           UnaryOperation
    * */
    private TripleExpression parseArgument()
            throws ExpressionParsingException {
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
            return new CheckedNegate(Evaluateable.toEvaluateable(parseArgument()));
        } else {
            try {
                return handleToken();
            } catch (TokenExprParsingException e) {
                throw new ArgExprParsingException("argument not found "
                        + exprBuilder.afterOperationError(), e);
            }
        }
    }

    private Evaluateable handleToken()
            throws ExpressionParsingException {
        String token = parseToken();
        try {
            if (Metadata.TRIPLE_EXPR_VAR_TOKENS.contains(token)) {
                return new Variable(token);
            } else if (token.equals("log10")) {
                return new Log10(Evaluateable.toEvaluateable(parseArgument()));
            } else if (token.equals("pow10")) {
                return new Pow10(Evaluateable.toEvaluateable(parseArgument()));
            } else if (token.equals("count")) {
                return new Count(Evaluateable.toEvaluateable(parseArgument()));
            } else {
                throw new VarExprParsingException("invalid variable name '" + token + "'");
            }
        } catch (ArgExprParsingException e) {
            throw new ArgExprParsingException(e.getMessage() + " after '" + token + "'");
        }
    }

    private Operation parseBinaryOperation() throws OpExprParsingException {
        if (take('+')) {
            return new CheckedAdd(null, null);
        } else if (take('-')) {
            return new CheckedSubtract(null, null);
        } else if (take('*')) {
            return new CheckedMultiply(null, null);
        } else if (take('/')) {
            return new CheckedDivide(null, null);
        } else {
            String token;
            try {
                token = parseToken();
            } catch (TokenExprParsingException e) {
                throw new OpExprParsingException("invalid operation", e);
            }
            if (token.equals("set")) {
                return new Set(null, null);
            } else if (token.equals("clear")) {
                return new Clear(null, null);
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

    private Const parseConst(final String beginning) throws ConstExprParsingException {
        final StringBuilder sb = new StringBuilder(beginning);
        int value;
        try {
            takeInteger(sb);
            value = Integer.parseInt(sb.toString());
        } catch (NumberFormatException e) {
            throw new ConstExprParsingException("invalid const");
        }
        return new Const(value);
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