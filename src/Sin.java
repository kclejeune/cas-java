/**
 * Represents the trigonometric sine function
 *
 * @author Kennan LeJeune
 */
public class Sin extends UnaryOp {
    /**
     * Initializes the operand argument to be taken by a Sin function
     *
     * @param operand The operand to be taken by the Sin function
     */
    public Sin(Function operand) {
        super(operand);
    }

    /**
     * Computes the value of a Sin function at the value of an input parameter
     *
     * @param input The value at which to evaluate the function
     * @return The value of the Sin function at the value of the input parameter
     */
    @Override
    public double value(double input) {
        return Math.sin(this.getOperand().value(input));
    }

    /**
     * Computes the value of a numeric Sin function
     *
     * @return The value of the Sin function
     */
    @Override
    public double value() {
        return Math.sin(this.getOperand().value());
    }

    /**
     * Computes the derivative of a Sin function
     *
     * @return The function which is the derivative of a Sin function instance
     */
    @Override
    public Function derivative() {
        return new BinaryOp(new Cos(getOperand()), getOperand().derivative(), BinaryOp.Op.MULT);
    }
}
