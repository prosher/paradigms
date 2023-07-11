package expression.parser;

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

    protected void expect(final String expected) {
        for (final char ch : expected.toCharArray()) {
            expect(ch);
        }
    }

    protected void expectWord(final String expected) {
        expect(expected);
        if (ch != END && Character.isLetter(ch)) {
            throw error("Expected '..." + expected
                    + "', found '..." + expected + errorChar() + "...'");
        }
    }

    protected void expect(final char expected) {
        if (!take(expected)) {
            throw error("Expected '" + expected + "', found '" + errorChar() + "'");
        }
    }

    protected IllegalArgumentException error(final String message) {
        return source.error(message);
    }

    protected boolean eof() {
        return take(END);
    }

    protected String errorChar() {
        return ch == END ? "EOF" : "'" + ch + "'";
    }

    protected boolean between(final char right, final char left) {
        return right <= ch && ch <= left;
    }
}
