/**
 * Represents the trigonometric cosine function
 *
 * @author Kennan LeJeune
 */
public class Cos extends UnaryOp {
    /**
     * Initializes the operand argument to be taken by a Cos function instance
     *
     * @param operand the operand to be taken by the Cos instance
     */
    public Cos(Function operand) {
        super(operand);
    }

    /**
     * @return the value of a Cos instance
     */
    @Override
    public double value() {
        return Math.cos(this.getOperand().value());
    }

    /**
     * @param input the value at which to evaluate the function
     * @return the value of the Cos function at the given input value
     */
    @Override
    public double value(double input) {
        return Math.cos(this.getOperand().value(input));
    }

    /**
     * Computes the derivative of a Cos function
     *
     * @return The Function which is the derivative of the Cos function
     */
    @Override
    public Function derivative() {
        return new BinaryOp(new BinaryOp(new Number(-1), new Sin(getOperand()), BinaryOp.Op.MULT), getOperand()
                .derivative(), BinaryOp.Op.MULT);
    }
}
