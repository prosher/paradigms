package expression.generic;

import expression.exceptions.ExpressionParsingException;
import expression.generic.base.ExpressionParser;
import expression.generic.base.TripleExpression;
import expression.generic.calculators.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private final static Map<String, Calculator<?>> MODE_TO_CALCULATOR = Map.of(
            "i", CheckedIntegerCalculator.INSTANCE,
            "d", DoubleCalculator.INSTANCE,
            "bi", BigIntegerCalculator.INSTANCE,
            "u", IntegerCalculator.INSTANCE,
            "l", LongCalculator.INSTANCE,
            "s", ShortCalculator.INSTANCE
    );

    private <T> Object[][][] calcTable(final Calculator<T> calculator, final String expression,
                                       final int x1, final int x2,
                                       final int y1, final int y2,
                                       final int z1, final int z2) throws ExpressionParsingException {
        final TripleExpression<T> expr = new ExpressionParser<>(calculator).parse(expression);
        final int dx = x2 - x1;
        final int dy = y2 - y1;
        final int dz = z2 - z1;
        final Object[][][] table = new Object[dx + 1][dy + 1][dz + 1];
        for (int x = 0; x <= dx; x++) {
            for (int y = 0; y <= dy; y++) {
                for (int z = 0; z <= dz; z++) {
                    try {
                        table[x][y][z] = expr
                                .evaluate(calculator.castInt(x1 + x),
                                        calculator.castInt(y1 + y),
                                        calculator.castInt(z1 + z));
                    } catch (Exception e) {
                        table[x][y][z] = null;
                    }
                }
            }
        }
        return table;
    }

    @Override
    public Object[][][] tabulate(final String mode, final String expression,
                                 final int x1, final int x2,
                                 final int y1, final int y2,
                                 final int z1, final int z2) throws ExpressionParsingException {
        return calcTable(MODE_TO_CALCULATOR.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }
}
