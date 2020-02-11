/**
 * Represents any Function which takes a single input and produces a single output
 *
 * @author Kennan LeJeune
 */
public abstract class UnaryOp extends Function {

    //stores the function that is the operand of a Unary Operation
    private final Function operand;

    //stores the name of the UnaryOp instance true type
    private final String functionName;

    /**
     * Initializes the operand and name of a Function that is a Unary Operation
     *
     * @param operand The operand to be taken as a function argument
     */
    public UnaryOp(Function operand) {
        this.operand = operand;

        //returns "class TypeName", so substring(6, "class TypeName".length()) returns "TypeName"
        this.functionName = this.getClass().toString().substring(6);
    }

    /**
     * Retrieve the name of the UnaryOp true type
     *
     * @return The name of the current type of the Unary Operation
     */
    public String getFunctionName() {
        return this.functionName;
    }

    /**
     * Retrieves the operand (argument) of a UnaryOp instance
     *
     * @return The Function which is the operand of a Unary Operation
     */
    public Function getOperand() {
        return this.operand;
    }

    /**
     * Compute the String representation of a UnaryOp instance
     *
     * @return The string which represents a Unary Operation
     */
    @Override
    public String toString() {
        return getFunctionName() + "[" + this.getOperand().toString() + "]";
    }

    /**
     * Compares a UnaryOp instance to an input function
     *
     * @param input the Function to be compared
     * @return Whether or not the input function is equivalent to the UnaryOp instance
     */
    @Override
    public boolean equals(Object input) {
        //return whether the input true type is an instance of the UnaryOp true type
        return input.getClass().isInstance(this) && ((UnaryOp) input).getOperand().equals(this.getOperand());
    }
}
	

