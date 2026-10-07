import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayStackTest {

    // emptyStack Tests
    @Test
    public void emptyStack1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        assertTrue(stack.isEmpty());
    }

    // push Tests
    @Test
    public void push1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        stack.push(5);
        assertEquals(1, stack.size());
    }

    // peek Tests
    @Test
    public void peek1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        stack.push(10);
        assertEquals(10, stack.peek());
    }

    @Test
    public void peek2() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        assertThrows(IndexOutOfBoundsException.class, () -> stack.peek());
    }

    // pop Tests
    @Test
    public void pop1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        stack.push(42);
        assertEquals(42, stack.pop());
    }

    @Test
    public void pop2() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        assertThrows(IndexOutOfBoundsException.class, () -> stack.pop());
    }

    // isEmpty Tests
    @Test
    public void isEmpty1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void isEmpty2() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        stack.push(1);
        assertFalse(stack.isEmpty());
    }

    // size Tests
    @Test
    public void size1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        assertEquals(0, stack.size());
    }

    @Test
    public void resize1() {
        ArrayStack<Integer> stack = ArrayStack.emptyStack();
        for (int i = 0; i < 15; i++) {
            stack.push(i);
        }
        assertEquals(15, stack.size());
    }
}