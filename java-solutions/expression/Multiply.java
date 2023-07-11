package expression;

public class Multiply extends BinaryOperation {

    private final static String SYMBOL = "*";

    public Multiply(Evaluateable left, Evaluateable right) {
        super(left, right);
    }

    @Override
    protected int calc(int left, int right) {
        return left * right;
    }

    @Override
    protected double calc(double left, double right) {
        return left * right;
    }

    @Override
    public String getSymbol() {
        return SYMBOL;
    }

}
