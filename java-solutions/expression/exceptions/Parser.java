package expression.exceptions;

public class Parser {
    public static final char END = '\0';
    protected CharSource source;
    protected char ch;

    protected void setSource(final CharSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected void expect(final String expected) throws ExpectExprParsingException {
        for (final char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    protected void expect(final char expected) throws ExpectExprParsingException {
        if (!take(expected)) {
            expectError(expected, ch);
        }
    }

    protected void expectError(final char expected, final char actual)
            throws ExpectExprParsingException {
        throw new ExpectExprParsingException("Expected '" + expected + "', found " + errorChar(actual));
    }

    protected boolean eof() {
        return take(END);
    }

    protected String errorChar(final char c) {
        return c == END ? "end of file" : "'" + c + "'";
    }

    protected boolean between(final char right, final char left) {
        return right <= ch && ch <= left;
    }
}
