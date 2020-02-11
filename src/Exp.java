/**
 * Represents a base e exponential function
 *
 * @author Kennan LeJeune
 */
public class Exp extends UnaryOp {
    /**
     * Initializes the operand argument to be taken by an Exp function instance
     *
     * @param operand the operand to be taken by the Exp instance
     */
    public Exp(Function operand) {
        super(operand);
    }

    /**
     * Evaluates an Exp function at a given input value
     *
     * @param input The value at which to evaluate the function
     * @return The value of the Exp function at the value of the input parameter
     */
    public double value(double input) {
        return Math.exp(this.getOperand().value(input));
    }

    /**
     * Computes the value of a numeric Exp function
     *
     * @return The value of the Exp function
     */
    public double value() {
        return Math.exp(this.getOperand().value());
    }

    /**
     * Computes the derivative of an Exp function
     *
     * @return The function that is the derivative of the Exp function
     */
    public Function derivative() {
        return new BinaryOp(this, getOperand().derivative(), BinaryOp.Op.MULT);
    }

}
