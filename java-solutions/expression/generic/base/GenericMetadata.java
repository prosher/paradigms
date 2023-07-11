package expression.generic.base;

import expression.generic.operations.*;

import java.util.List;
import java.util.Map;

public final class GenericMetadata {
    public static final String LP = "(";
    public static final String RP = ")";
    public static final Map<Class<?>, Integer> PRIORITY = Map.ofEntries(
            Map.entry(Mod.class, 3),
            Map.entry(Square.class, -1),
            Map.entry(Abs.class, -1),

            Map.entry(Negate.class, 2),
            Map.entry(Const.class, 2),
            Map.entry(Variable.class, 2),
            Map.entry(Multiply.class, 1),
            Map.entry(Divide.class, 1),
            Map.entry(Add.class, 0),
            Map.entry(Subtract.class, 0)
    );

    public static final List<Class<?>> ASYMMETRIC = List.of(
            Subtract.class,
            Divide.class
    );

    public static final List<Class<?>> ORDERED = List.of(
            Divide.class
    );

    public static final Map<Class<?>, String> OPERATION_TO_STRING = Map.ofEntries(
            Map.entry(Mod.class, "mod"),
            Map.entry(Square.class, "square"),
            Map.entry(Abs.class, "abs"),

            Map.entry(Negate.class, "-"),
            Map.entry(Multiply.class, "*"),
            Map.entry(Divide.class, "/"),
            Map.entry(Add.class, "+"),
            Map.entry(Subtract.class, "-")
    );

    public static final Map<String, Class<?>> STRING_TO_BINARY = Map.ofEntries(
            Map.entry("mod", Mod.class),
            Map.entry("*", Multiply.class),
            Map.entry("/", Divide.class),
            Map.entry("+", Add.class),
            Map.entry("-", Subtract.class)
    );

    public static final List<String> TRIPLE_EXPR_VAR_TOKENS = List.of(
            "x", "y", "z"
    );
}
