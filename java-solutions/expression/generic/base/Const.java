package expression.generic.base;

import java.util.Objects;

public final class Const<T> implements TripleExpression<T> {
    private final T value;

    public Const(T value) {
        this.value = value;
    }

    @Override
    public T evaluate(final T x, final T y, final T z) {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public String toMiniString() {
        return toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj != null && obj.getClass() == Const.class &&
                Objects.equals(this.value, ((Const<?>) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode() * 37 + Const.class.hashCode();
    }
}
