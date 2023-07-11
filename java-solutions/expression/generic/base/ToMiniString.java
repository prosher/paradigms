package expression.generic.base;

public interface ToMiniString {
    default String toMiniString() {
        return toString();
    }
}
