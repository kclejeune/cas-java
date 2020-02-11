/**
 * Represents any mathematical function
 *
 * @author Kennan LeJeune
 */
public abstract class Function {
    /**
     * Computes the value of a strictly numerical (containing no variables) Function instance
     *
     * @return The value of the function
     */
    public abstract double value();

    /**
     * Computes the value of a Function instance composed of either variables or numbers
     *
     * @param input The value at which to evaluate the function
     * @return The value of the function at the given input
     */
    public abstract double value(double input);

    /**
     * Computes the String representation of a Function instance
     *
     * @return The String representation of a Function instance
     */
    @Override
    public abstract String toString();

    /**
     * Computes the derivative of a Function instance
     *
     * @return The Function that is the derivative of a Function instance
     */
    public abstract Function derivative();

    /**
     * Compare whether an input object is equivalent to a Function instance
     *
     * @param input The function to be compared
     * @return Whether or not the functions are equivalent
     */
    @Override
    public abstract boolean equals(Object input);
}
