package expression;

import java.util.*;

public abstract class Operation implements Evaluateable {
    private Evaluateable[] args;
    private final int argsCount;

    protected Operation(Evaluateable[] args) {
        this.args = args;
        this.argsCount = args.length;
    }

    public void setArgs(Evaluateable[] newArgs) {
        if (newArgs.length != argsCount) {
            throw new IllegalArgumentException(
                    "expected " + argsCount + " arguments, found " + newArgs.length
            );
        }
        args = newArgs;
    }

    public Evaluateable getArgument(int i) {
        return args[i];
    }

    public int getArgsCount() {
        return argsCount;
    }

    public abstract String getSymbol(int i);

    protected abstract int calc(int[] evaluatedArgs);

    @Override
    public final int evaluate(int x) {
        int[] evaluatedArgs = new int[argsCount];
        for (int i = 0; i < argsCount; i++) {
            evaluatedArgs[i] = args[i].evaluate(x);
        }
        return calc(evaluatedArgs);
    }

    @Override
    public int evaluateMultiple(Map<String, Integer> vars) {
        int[] evaluatedArgs = new int[argsCount];
        for (int i = 0; i < argsCount; i++) {
            evaluatedArgs[i] = args[i].evaluateMultiple(vars);
        }
        return calc(evaluatedArgs);
    }

    @Override
    public final int evaluate(int x, int y, int z) {
        return evaluateMultiple(Map.of("x", x, "y", y, "z", z));
    }

    protected abstract double calc(double[] evaluatedArgs);

    @Override
    public double evaluate(double x) {
        double[] evaluatedArgs = new double[argsCount];
        for (int i = 0; i < argsCount; i++) {
            evaluatedArgs[i] = args[i].evaluate(x);
        }
        return calc(evaluatedArgs);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(Metadata.LP);
        sb.append(getArgument(0)).append(" ");
        for (int i = 1; i < argsCount; i++) {
            sb.append(getSymbol(i - 1)).append(" ").append(getArgument(i));
        }
        sb.append(Metadata.RP);
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        if (argsCount == 2) {
            return (this).toMiniString();
        } else {
            return toString();
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operation other) {
            return this.getClass() == other.getClass() &&
                    Arrays.equals(this.args, other.args);
        }
        return false;
    }


    @Override
    public int hashCode() {
        return Arrays.hashCode(this.args) * 37 + this.getClass().hashCode();
    }
}
