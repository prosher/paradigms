package expression;

public interface Evaluateable extends Expression, MultipleExpression, DoubleExpression {
    static Evaluateable toEvaluateable(Expression expr) {
        return (Evaluateable) expr;
    }
    static Evaluateable toEvaluateable(MultipleExpression expr) {
        return (Evaluateable) expr;
    }
    static Evaluateable toEvaluateable(TripleExpression expr) {
        return (Evaluateable) expr;
    }
    static Evaluateable toEvaluateable(DoubleExpression expr) {
        return (Evaluateable) expr;
    }

}
