public record NumNode(double value) implements AST {
    /** Returns the value saved in numNode */
    @Override
    public double eval() {
        return value;
    }
}
