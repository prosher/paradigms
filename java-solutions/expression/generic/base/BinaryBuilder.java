package expression.generic.base;

import expression.exceptions.OpExprParsingException;
import expression.generic.calculators.Calculator;
import expression.generic.operations.*;

import java.util.*;

public class BinaryBuilder<T> {
    private final Calculator<T> calculator;
    private final Deque<String> operationSymbols;
    private final Deque<TripleExpression<T>> expressions;
    private final Deque<Integer> operationLevels;
    private int curLevel;

    public BinaryBuilder(Calculator<T> calculator) {
        this.calculator = calculator;
        this.operationSymbols = new ArrayDeque<>();
        this.expressions = new ArrayDeque<>();
        this.operationLevels = new ArrayDeque<>();
        this.curLevel = 0;
    }

    public void addOperation(final String opSymbol) {
        operationSymbols.push(opSymbol);
        operationLevels.push(curLevel);
    }

    public void addExpression(final TripleExpression<T> value) {
        expressions.push(value);
    }

    public void beginParentheses() {
        curLevel++;
    }

    public void endParentheses() throws OpExprParsingException {
        squashInScope();
        curLevel--;
    }

    private boolean buildBinary(final String opSymbol) throws OpExprParsingException {
        TripleExpression<T> arg2 = expressions.peek();
        expressions.pop();
        TripleExpression<T> arg1 = expressions.peek();
        expressions.pop();
        expressions.push(switch (opSymbol) {
            case "+" -> new Add<>(calculator, arg1, arg2);
            case "-" -> new Subtract<>(calculator, arg1, arg2);
            case "*" -> new Multiply<>(calculator, arg1, arg2);
            case "/" -> new Divide<>(calculator, arg1, arg2);
            case "mod" -> new Mod<>(calculator, arg1, arg2);
            default -> throw new OpExprParsingException("unknown operation '" + opSymbol + "'");
        });
        return true;
    }

    public String afterOperationError() {
        StringBuilder message = new StringBuilder();
        if (inScope()) {
            message.append("after operation '")
                    .append(operationSymbols.peek())
                    .append("'");
        }
        if (curLevel > 0) {
            message.append(" in level ").append(curLevel).append(" parentheses");
        }
        if (message.isEmpty()) {
            message.append("in expression beginning");
        }
        return message.toString();
    }

    private boolean inScope() {
        return operationLevels.size() > 0 && curLevel == operationLevels.peek();
    }

    private void squashInScope(final int priority) throws OpExprParsingException {
        while (inScope() && GenericMetadata.PRIORITY.get(
                GenericMetadata.STRING_TO_BINARY.get(operationSymbols.peek())) > priority) {
            squash();
        }
        if (inScope() && GenericMetadata.PRIORITY.get(
                GenericMetadata.STRING_TO_BINARY.get(operationSymbols.peek())) == priority) {
            squash();
        }
    }

    private void squash() throws OpExprParsingException {
        operationLevels.pop();
        buildBinary(operationSymbols.pop());
    }

    private void squashInScope() throws OpExprParsingException {
        squashInScope(Integer.MIN_VALUE);
    }

    public TripleExpression<T> getExpression() throws IllegalStateException, OpExprParsingException {
        squashInScope();
        return expressions.pop();
    }

    public void clear() {
        operationSymbols.clear();
        expressions.clear();
        operationLevels.clear();
        curLevel = 0;
    }
}
