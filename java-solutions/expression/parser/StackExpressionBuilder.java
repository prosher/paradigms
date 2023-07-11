package expression.parser;

import expression.*;

import java.util.Stack;

public class StackExpressionBuilder implements ExpressionBuilder {
    private final Stack<Operation> operations;
    private final Stack<Evaluateable> expressions;
    private final Stack<Integer> operationLevels;
    private int curLevel;

    public StackExpressionBuilder() {
        operations = new Stack<>();
        expressions = new Stack<>();
        operationLevels = new Stack<>();
        curLevel = 0;
    }

    @Override
    public void addOperation(final Operation op) {
        squashInScope(Metadata.PRIORITY.get(op.getClass()));
        operations.push(op);
        operationLevels.push(curLevel);
    }

    @Override
    public void addExpression(final Evaluateable value) {
        expressions.push(value);
    }

    @Override
    public void beginParentheses() {
        curLevel++;
    }

    @Override
    public void endParentheses() {
        squashInScope();
        curLevel--;
    }

    private boolean buildExpressionOnTop(final Operation op) {
        final int size = op.getArgsCount();
        if (size > expressions.size()) {
            return false;
        }
        Evaluateable[] args = new Evaluateable[size];
        for (int i = size - 1; i >= 0; i--) {
            args[i] = expressions.pop();
        }
        op.setArgs(args);
        expressions.push(op);
        return true;
    }

    private boolean inScope() {
        return operations.size() > 0 && curLevel == operationLevels.peek();
    }

    private void squashInScope(final int priority) {
        while (inScope() && Metadata.PRIORITY.get(operations.peek().getClass()) > priority) {
            squash();
        }
        if (inScope() && Metadata.PRIORITY.get(operations.peek().getClass()) == priority) {
            squash();
        }
    }

    private void squash() {
        operationLevels.pop();
        buildExpressionOnTop(operations.pop());
    }

    private void squashInScope() {
        squashInScope(Integer.MIN_VALUE);
    }

    @Override
    public Evaluateable getExpression() throws IllegalStateException {
        squashInScope();
        return expressions.pop();
    }

    @Override
    public void clear() {
        operations.clear();
        expressions.clear();
        operationLevels.clear();
        curLevel = 0;
    }
}
