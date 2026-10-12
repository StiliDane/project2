import java.util.Arrays;

public class ArrayStack<T> {
    // VARS
    private Object[] array;
    private int size;

    //===============================================================
    // CONSTRUCTOR
    public ArrayStack(){
        array = new Object[10];
        size = 0;
    }

    //===============================================================
    // PUBLIC METHODS

    /** Returns a new empty stack */
    public static <T> ArrayStack<T> emptyStack(){
        return new ArrayStack<T>();
    }

    /** add a new element to the front of the stack */
    public void push(T val){
        checkAndResize();

        this.array[this.size] = val;
        size++;
    }

    /** looks at the next element in the stack and remove it */
    public T pop(){
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }

        T output = peek();

        size--;
        this.array[this.size] = null;

        return output;
    }

    /** Looks at the next element in the stack */
    @SuppressWarnings("unchecked")
    public T peek(){
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException("Stack is empty");
        }

        return (T) this.array[this.size - 1];
    }

    /** Returns true if the stack currently contains no elements, false if otherwise */
    public boolean isEmpty(){
        return size == 0;
    }

    /** Return the current number of elements of the stack */
    public int size(){
        return size;
    }

    //===============================================================
    // PRIVATE METHODS

    /** If the array is maxed out on size double the length and copy it to the new array */
    private void checkAndResize(){
        if (this.size != this.array.length)
            return;

        this.array = Arrays.copyOf(array, size * 2);
    }
}