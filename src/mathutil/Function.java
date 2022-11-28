package mathutil;

/**
 * 
 * A functional interface to represent an arbitrary function of one variable
 * 
 * @author Richard Williams
 * @since 11/22/2022
 */
public interface Function {

	/**
	 * The definition for a function of t
	 * @param t
	 * @return f(t)
	 */
	public double evaluate(double t);
	
}
