import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ASTTest {

    @Test
    public void evalNumNode1() {
        AST node = new NumNode(5.0);
        assertEquals(5.0, node.eval(), 0.0001);
    }

    @Test
    public void evalNumNode2() {
        AST node = new NumNode(-3.5);
        assertEquals(-3.5, node.eval(), 0.0001);
    }

    @Test
    public void evalAdd1() {
        AST node = new BinopNode("+", new NumNode(3.0), new NumNode(4.0));
        assertEquals(7.0, node.eval(), 0.0001);
    }

    @Test
    public void evalSubtract1() {
        AST node = new BinopNode("-", new NumNode(10.0), new NumNode(4.0));
        assertEquals(6.0, node.eval(), 0.0001);
    }

    @Test
    public void evalMultiply1() {
        AST node = new BinopNode("*", new NumNode(3.0), new NumNode(5.0));
        assertEquals(15.0, node.eval(), 0.0001);
    }

    @Test
    public void evalDivide1() {
        AST node = new BinopNode("/", new NumNode(20.0), new NumNode(4.0));
        assertEquals(5.0, node.eval(), 0.0001);
    }

    @Test
    public void evalPower1() {
        AST node = new BinopNode("^", new NumNode(2.0), new NumNode(3.0));
        assertEquals(8.0, node.eval(), 0.0001);
    }

    @Test
    public void evalNested1() {
        AST addNode = new BinopNode("+", new NumNode(3.0), new NumNode(4.0));
        AST rootNode = new BinopNode("*", new NumNode(2.0), addNode);
        assertEquals(14.0, rootNode.eval(), 0.0001);
    }

    @Test
    public void evalInvalidOperator1() {
        AST node = new BinopNode("%", new NumNode(5.0), new NumNode(2.0));
        assertThrows(IllegalArgumentException.class, () -> node.eval());
    }
}