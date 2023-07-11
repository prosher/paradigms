package expression.generic.base;

import java.util.Map;

public class Variable<T> implements TripleExpression<T> {

    private final String entry;

    public Variable(String entry) {
        this.entry = entry;
    }

    @Override
    public T evaluate(final T x, final T y, final T z) {
        T val = Map.of("x", x, "y", y, "z", z).getOrDefault(entry, null);
        if (val != null) {
            return val;
        } else {
            throw new IllegalStateException("variable " + entry + " is not evaluated");
        }
    }

    @Override
    public String toString() {
        return entry;
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj.getClass() == Variable.class &&
                this.entry.equals(((Variable<?>) obj).entry);
    }

    @Override
    public int hashCode() {
        return this.entry.hashCode() * 37 + Variable.class.hashCode();
    }
}
