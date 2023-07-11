package expression;

public class Subtract extends BinaryOperation {

    private final static String SYMBOL = "-";

    public Subtract(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        return left - right;
    }

    @Override
    protected double calc(double left, double right) {
        return left - right;
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

}
