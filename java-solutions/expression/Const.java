package expression;

import java.util.Map;
import java.util.Objects;

public final class Const implements Evaluateable {
    private final Number value;

    public Const(int value) {
        this.value = value;
    }

    public Const(double value) {
        this.value = value;
    }

    @Override
    public int evaluate(int var) {
        return (int) value;
    }

    @Override
    public int evaluateMultiple(Map<String, Integer> vars) {
        return (int) value;
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
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == Const.class &&
                Objects.equals(this.value, ((Const) obj).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode() * 37 + Const.class.hashCode();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }

    @Override
    public double evaluate(double x) {
        return (double) value;
    }
}
