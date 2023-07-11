package expression;

import expression.exceptions.*;
import expression.parser.Clear;
import expression.parser.Count;
import expression.parser.Negate;
import expression.parser.Set;

import java.util.List;
import java.util.Map;

public final class Metadata {
    public static final String LP = "(";
    public static final String RP = ")";
    public static final Map<Class<?>,Integer> PRIORITY = Map.ofEntries(
            Map.entry(Pow10.class, 2),
            Map.entry(Log10.class, 2),

            Map.entry(Negate.class, 2),
            Map.entry(Count.class, 2),

            Map.entry(Const.class, 2),
            Map.entry(Variable.class, 2),
            Map.entry(Multiply.class, 1),
            Map.entry(Divide.class, 1),
            Map.entry(Add.class, 0),
            Map.entry(Subtract.class, 0),

            Map.entry(Set.class, -1),
            Map.entry(Clear.class, -1),

            Map.entry(CheckedNegate.class, 2),
            Map.entry(CheckedMultiply.class, 1),
            Map.entry(CheckedDivide.class, 1),
            Map.entry(CheckedAdd.class, 0),
            Map.entry(CheckedSubtract.class, 0)
    );

    public static final List<Class<?>> ASYMMETRIC = List.of(
            Subtract.class,
            Divide.class,
            CheckedSubtract.class,
            CheckedDivide.class,
            Set.class,
            Clear.class
    );

    public static final List<Class<?>> ORDERED = List.of(
            Divide.class,
            CheckedDivide.class
    );

    public static final Map<Class<?>, String> OPERATION_TO_STRING = Map.ofEntries(
            Map.entry(Pow10.class, "pow10"),
            Map.entry(Log10.class, "log10"),

            Map.entry(Negate.class, "-"),
            Map.entry(Count.class, "count"),

            Map.entry(Multiply.class, "*"),
            Map.entry(Divide.class, "/"),
            Map.entry(Add.class, "+"),
            Map.entry(Subtract.class, "-"),

            Map.entry(Set.class, "set"),
            Map.entry(Clear.class, "clear"),

            Map.entry(CheckedNegate.class, "-"),
            Map.entry(CheckedMultiply.class, "*"),
            Map.entry(CheckedDivide.class, "/"),
            Map.entry(CheckedAdd.class, "+"),
            Map.entry(CheckedSubtract.class, "-")
    );

    public static final List<String> TRIPLE_EXPR_VAR_TOKENS = List.of(
            "x", "y", "z"
    );
}
