package expression.generic.calculators;

public interface Calculator<T> {
    T add(T left, T right);

    T subtract(T left, T right);

    T multiply(T left, T right);

    T divide(T left, T right);

    T negate(T value);

    T castInt(int value);

    T mod(T left, T right);

    T abs(T value);

    T square(T value);
}
