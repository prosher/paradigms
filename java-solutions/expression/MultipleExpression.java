package expression;

import java.util.Map;

public interface MultipleExpression extends TripleExpression {
    int evaluateMultiple(Map<String,Integer> vars);
}
