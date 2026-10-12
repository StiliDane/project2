import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    // parsePostfix Tests

    @Test
    public void parsePostfix1() {
        AST result = Parser.parsePostfix("3 4 +");
        assertEquals(new BinopNode("+", new NumNode(3.0), new NumNode(4.0)), result);
    }

    @Test
    public void parsePostfix2() {
        AST result = Parser.parsePostfix("3 4 2 * +");
        AST expected = new BinopNode("+",
                new NumNode(3.0),
                new BinopNode("*", new NumNode(4.0), new NumNode(2.0))
        );
        assertEquals(expected, result);
    }

    @Test
    public void parsePostfix3() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parsePostfix4() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("  "));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parsePostfix5() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 a +"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    public void parsePostfix6() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    public void parsePostfix7() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix("2 3 4 +"));
        assertEquals("too many operands", e.getMessage());
    }

    // parseInfix Tests

    @Test
    public void parseInfix1() {
        AST result = Parser.parseInfix("3 + 4");
        assertEquals(new BinopNode("+", new NumNode(3.0), new NumNode(4.0)), result);
    }

    @Test
    public void parseInfix2() {
        AST result = Parser.parseInfix("2 + 3 * 4");
        AST expected = new BinopNode("+",
                new NumNode(2.0),
                new BinopNode("*", new NumNode(3.0), new NumNode(4.0))
        );
        assertEquals(expected, result);
    }

    @Test
    public void parseInfix3() {
        AST result = Parser.parseInfix("( 2 + 3 ) * 4");
        AST expected = new BinopNode("*",
                new BinopNode("+", new NumNode(2.0), new NumNode(3.0)),
                new NumNode(4.0)
        );
        assertEquals(expected, result);
    }

    @Test
    public void parseInfix4() {
        AST result = Parser.parseInfix("2 ^ 3 ^ 2");
        AST expected = new BinopNode("^",
                new NumNode(2.0),
                new BinopNode("^", new NumNode(3.0), new NumNode(2.0))
        );
        assertEquals(expected, result);
    }

    @Test
    public void parseInfix5() {
        assertEquals(14.0, Parser.parseInfix("2 * ( 3 + 4 )").eval(), 0.0001);
    }

    @Test
    public void parseInfix6() {
        AST result = Parser.parseInfix("3 / 4");
        assertEquals(new BinopNode("/", new NumNode(3.0), new NumNode(4.0)), result);
    }

    @Test
    public void parseInfixPrecedence1() {
        AST result = Parser.parseInfix("3 * 4 + 2");
        AST expected = new BinopNode("+",
                new BinopNode("*", new NumNode(3.0), new NumNode(4.0)),
                new NumNode(2.0)
        );
        assertEquals(expected, result);
    }

    @Test
    public void parseInfixEmpty1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix(""));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parseInfixInvalidToken1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 + a"));
        assertEquals("invalid token", e.getMessage());
    }

    @Test
    public void parseInfixInsufficientOperands1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 +"));
        assertEquals("insufficient operands", e.getMessage());
    }

    @Test
    public void parseInfixTooManyOperands1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 + 3 4"));
        assertEquals("too many operands", e.getMessage());
    }

    @Test
    public void parseInfixMismatchedOpenParen1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( 2 + 3"));
        assertEquals("mismatched open paren", e.getMessage());
    }

    @Test
    public void parseInfixMismatchedCloseParen1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("2 + 3 )"));
        assertEquals("mismatched close paren", e.getMessage());
    }

    @Test
    public void parsePostfixNull1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parsePostfix(null));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parseInfixNull1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix(null));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parseInfixEmptyParens1() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> Parser.parseInfix("( )"));
        assertEquals("empty input", e.getMessage());
    }

    @Test
    public void parseInfixMinus1() {
        AST result = Parser.parseInfix("5 - 3");
        assertEquals(new BinopNode("-", new NumNode(5.0), new NumNode(3.0)), result);
    }

    @Test
    public void parseInfixMixedPrecedence1() {
        AST result = Parser.parseInfix("2 ^ 3 + 1");
        AST expected = new BinopNode("+",
                new BinopNode("^", new NumNode(2.0), new NumNode(3.0)),
                new NumNode(1.0)
        );
        assertEquals(expected, result);
    }

    @Test
    public void parseInfixChainedLeftAssoc1() {
        AST result = Parser.parseInfix("8 / 4 / 2");
        AST expected = new BinopNode("/",
                new BinopNode("/", new NumNode(8.0), new NumNode(4.0)),
                new NumNode(2.0)
        );
        assertEquals(expected, result);
    }
}