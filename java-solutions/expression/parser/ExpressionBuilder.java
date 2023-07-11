package expression.parser;

import expression.Evaluateable;
import expression.Operation;

public interface ExpressionBuilder {
    void addOperation(Operation op);
    void addExpression(Evaluateable op);
    void beginParentheses();
    void endParentheses();
    Evaluateable getExpression();
    void clear();
}
