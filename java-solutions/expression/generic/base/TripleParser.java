package expression.generic.base;

public interface TripleParser<T> {
    TripleExpression<T> parse(String expression) throws Exception;
}
