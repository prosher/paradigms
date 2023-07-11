package expression;

public abstract class UnaryOperation extends Operation {
    protected UnaryOperation(Evaluateable argument) {
        super(new Evaluateable[]{argument});
    }

    public Evaluateable getArgument() {
        return getArgument(0);
    }

    public abstract String getSymbol();
    public String getSymbol(int i) {
        if (i == 0) {
            return getSymbol();
        } else {
            throw new IllegalArgumentException("invalid symbol index");
        }
    }

    @Override
    protected final int calc(int[] evaluatedArgs) {
        return calc(evaluatedArgs[0]);
    }

    protected abstract int calc(int mid);

    @Override
    protected final double calc(double[] evaluatedArgs) {
        return calc(evaluatedArgs[0]);
    }

    protected abstract double calc(double mid);

    @Override
    public String toString() {
        return getSymbol() + Metadata.LP + getArgument().toString() + Metadata.RP;
    }


    @Override
    public String toMiniString() {
        if (Metadata.PRIORITY.get(getArgument().getClass())
                < Metadata.PRIORITY.get(this.getClass())) {
            return getSymbol() + Metadata.LP + getArgument().toMiniString() + Metadata.RP;
        } else {
            return getSymbol() + " " + getArgument().toMiniString();
        }
    }
}
