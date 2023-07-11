package expression.generic;

import expression.exceptions.ExpressionParsingException;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Too many arguments! " +
                    "Expected input: <type parameter> <expression>");
            return;
        }
        String mode = args[0].substring(1);
        String expr = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        System.out.println("You entered expression \"" + expr + "\" in \"" + mode + "\" mode");

        final int x1 = -2, x2 = 2, y1 = -2, y2 = 2, z1 = -2, z2 = 2;
        Object[][][] table = new Object[0][][];
        try {
            table = new GenericTabulator().tabulate(
                    mode, expr,
                    x1, x2, y1, y2, z1, z2
            );
        } catch (ExpressionParsingException e) {
            System.out.println("Can't parse entered expression: " + e.getMessage());
        }
        System.out.println("| x | y | z |  Result  |");
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                for (int z = 0; z <= 4; z++) {
                    System.out.printf("|%3d|%3d|%3d|%10s|\n",
                            x1 + x, y1 + y, z1 + z, table[x][y][z]);
                }
            }
        }
    }
}
