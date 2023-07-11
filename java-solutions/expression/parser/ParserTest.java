package expression.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import expression.*;

public class ParserTest {
    @Test
    public void testBasic() {
        valid("   5 ", new Const(5));
        valid("x+5", new Add(new Variable("x"), new Const(5)));
        valid(" x +    5 ", new Add(new Variable("x"), new Const(5)));
        valid("x *  3 -5", new Subtract(new Multiply(new Variable("x"), new Const(3)), new Const(5)));
    }

    @Test
    public void testConst() {
        valid("   5 ", new Const(5));
        valid("-1237", new Const(-1237));
    }

//    @Test
//    public void testParetheses() {
//        valid("4 * (3 + x)", new Subtract(new Variable("x"), new Const(5)));
//        valid("4*(3 + x)", new Subtract(new Variable("x"), new Const(5)));
//        valid("43 * x * ", new Subtract(new Variable("x"), new Const(5)));
//
//    }

    @Test
    public void testUnary() {

    }

    @Test
    public void testNegate() {
        valid("-x", new Negate(new Variable("x")));
        valid("- x", new Negate(new Variable("x")));
        valid("-(x)", new Negate(new Variable("x")));
        valid("- ( x) ", new Negate(new Variable("x")));
        valid("- - x", new Negate(new Negate(new Variable("x"))));
    }

    @Test
    public void testComplex() {
        valid("((y + y) + z)", new Object());
    }

    @Test
    public void testInvalid() {
        invalid("x + y + ");
        invalid("(x + y + z");
        invalid("(x + y + z ");
        invalid("x + y + z)");
        invalid("");
        invalid("a");
        invalid("+ set");
        invalid("set");
        invalid("x set 4");
        invalid("x sety");
        invalid("-");
        invalid("(x + y) - (4(");
        invalid("10 20");
        invalid("(())");
        invalid("+");
        invalid("@x - y");
        invalid("x @ - y");
        invalid("x - y@");
        invalid("x y");
        invalid("+ y - z");
        invalid("1 + (x *  / 9) + 3");
    }

    private static void invalid(String input) {
        try {
            TripleExpression result = new ExpressionParser().parse(input);
            throw new IllegalArgumentException(
                    "Invalid input: '" + input + "' parse as '" + result.toMiniString() + "'");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void valid(String expectedToParse, Object actual) {
        Assertions.assertEquals(new ExpressionParser().parse(expectedToParse), actual);
    }

}
