package expression;

import java.util.Map;

public class Variable implements Evaluateable {

    private final String entry;

    public Variable(String entry) {
        this.entry = entry;
    }

    @Override
    public int evaluate(int var) {
        return var;
    }

    @Override
    public int evaluateMultiple(Map<String, Integer> vars) {
        Integer val = vars.getOrDefault(entry, null);
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
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == Variable.class &&
                this.entry.equals(((Variable) obj).entry);
    }

    @Override
    public int hashCode() {
        return this.entry.hashCode() * 37 + Variable.class.hashCode();
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluateMultiple(Map.of("x", x, "y", y, "z", z));
    }
}
