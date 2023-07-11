package expression.exceptions;

import expression.TripleExpression;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Print and evaluate triple expression!");
        System.out.println("Enter the expression, end with ';'");
        Scanner sc = new Scanner(System.in);
        TripleExpression expr = null;
        try {
            expr = new ExpressionParser().parse(readUntilChar(sc, ';'));
        } catch (ExpressionParsingException e) {
            ExpressionParsingException rootCause = e;
            while (rootCause.getCause() instanceof ExpressionParsingException) {
                rootCause = (ExpressionParsingException) rootCause.getCause();
            }
            System.out.println("Unable to parse expression: " + rootCause.getMessage());
        }
        if (expr == null) {
            return;
        }
        System.out.printf("%nYou entered the expression: %s%n", expr.toMiniString());
        System.out.println("Enter x, y, z values (without commas): ");
        int[] values = readNIntegers(sc, 3);
        if (values == null) {
            return;
        }
        try {
            System.out.println("The result is " + expr.evaluate(values[0], values[1], values[2]));
        } catch (ExpressionEvaluatingException e) {
            ExpressionEvaluatingException rootCause = e;
            while (rootCause.getCause() instanceof ExpressionEvaluatingException) {
                rootCause = (ExpressionEvaluatingException) rootCause.getCause();
            }
            System.out.println("Unable to evaluate expression: " + rootCause.getMessage());
        }
    }

    private static String readUntilChar(final Scanner sc, final char end) {
        StringBuilder input = new StringBuilder();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            int semicolonIndex;
            if ((semicolonIndex = line.indexOf(end)) != -1) {
                input.append(line, 0, semicolonIndex);
                break;
            }
            input.append(line);
        }
        return input.toString();
    }

    private static int[] readNIntegers(final Scanner sc, final int n) {
        final int[] result = new int[n];
        while (true) {
            try {
                for (int i = 0; i < n; i++) {
                    result[i] = Integer.parseInt(sc.next());
                }
                break;
            } catch (NumberFormatException e) {
                sc.nextLine();
                System.out.println("Unexpected input.");
            } catch (NoSuchElementException e) {
                return null;
            }
        }
        return result;
    }
}
