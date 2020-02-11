/**
 * Represents a constant number value
 *
 * @author Kennan LeJeune
 */
public class Number extends Function {
    //stores the value of a number object
    private final double value;

    /**
     * Initializes the value of a Number instance
     *
     * @param value the value of the Number
     */
    public Number(double value) {
        this.value = value;
    }

    /**
     * Computes the value of a number at a given input parameter
     * Always equivalent to the initial value
     *
     * @param input The value at which to evaluate the function
     * @return The value of the number
     */
    @Override
    public double value(double input) {
        return this.value;
    }

    /**
     * @return The value of the number
     */
    @Override
    public double value() {
        return this.value;
    }

    /**
     * Computes a String representation of a Number
     *
     * @return The String representation of a Number
     */
    @Override
    public String toString() {
        return String.valueOf(this.value());
    }

    /**
     * Compares whether a Function input is equivalent to the current instance
     *
     * @param input the function to be compared
     * @return whether the current Number instance is equivalent to the input Function
     */
    @Override
    public boolean equals(Object input) {
        return input instanceof Number && this.value() == ((Number) input).value();
    }

    /**
     * Computes the derivative of the Number instance
     * The value of any constant is zero, so this is always zero
     *
     * @return A number object of value 0
     */
    @Override
    public Function derivative() {
        return new Number(0);
    }
}
