package expression;

public abstract class BinaryOperation extends Operation {

    protected BinaryOperation(Evaluateable left, Evaluateable right) {
        super(new Evaluateable[]{left, right});
    }

    public Evaluateable getLeft() {
        return getArgument(0);
    }

    public Evaluateable getRight() {
        return getArgument(1);
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
        return calc(evaluatedArgs[0], evaluatedArgs[1]);
    }

    protected abstract int calc(int left, int right);

    @Override
    protected final double calc(double[] evaluatedArgs) {
        return calc(evaluatedArgs[0], evaluatedArgs[1]);
    }

    protected abstract double calc(double left, double right);

    @Override
    public String toString() {
        return Metadata.LP + getLeft().toString() + " "
                + getSymbol() + " " + getRight().toString() + Metadata.RP;
    }


    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        final int thisPriority = Metadata.PRIORITY.get(this.getClass());
        final int leftPriority = Metadata.PRIORITY.get(getLeft().getClass());
        final int rightPriority = Metadata.PRIORITY.get(getRight().getClass());

        if (thisPriority > leftPriority) {
            sb.append(Metadata.LP)
                    .append(getLeft().toMiniString())
                    .append(Metadata.RP);
        } else {
            sb.append(getLeft().toMiniString());
        }
        sb.append(" ").append(this.getSymbol()).append(" ");
        if (thisPriority > rightPriority ||
                thisPriority == rightPriority && (Metadata.ASYMMETRIC.contains(this.getClass()) ||
                        Metadata.ORDERED.contains(getRight().getClass()))) {
            sb.append(Metadata.LP)
                    .append(getRight().toMiniString())
                    .append(Metadata.RP);
        } else {
            sb.append(getRight().toMiniString());
        }
        return sb.toString();
    }
}
