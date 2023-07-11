package expression;

import expression.parser.ExpressionParser;
import expression.parser.TripleParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x = sc.nextInt();
        Evaluateable var = new Variable("x");
        Evaluateable sqr = new Add(
                new Subtract(
                        new Multiply(var, var),
                        new Multiply(new Const(2), var)
                ),
                new Const(1)
        );
        System.out.println(sqr.toMiniString() + " = " + sqr.evaluate(x));
        TripleParser parser = new ExpressionParser();
        System.out.println(parser.parse("0").toMiniString());
    }
}
