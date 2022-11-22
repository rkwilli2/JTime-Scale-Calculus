package timescalecalculus;

import timescalecalculus.exceptions.*;

/**
 * 
 * An arbitrary, nonempty closed subset of the real numbers.
 * <br>
 * Concrete methods are applicable to the arbitrary time scale whereas the 
 * abstract methods are to be defined by the user when the other time scale
 * implementations included in the library are not sufficient.
 * 
 * @author Richard Williams
 * @since 11/22/2022
 */
public abstract class TimeScale {

	/**
	 * @param t - Some arbitrary element of the reals
	 * @return whether or not t is an element of our time scale
	 */
	public abstract boolean isInTimeScale(double t);
	
	/**
	 * The forward jump operator as defined by [1] Definition 1.1.
	 * @param t - Some arbitrary element of our time scale.
	 * @return The infimum of set of all items greater than t in our time scale.
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public abstract double sigma(double t) throws NotInTimeScaleException;
	
	/**
	 * The backward jump operator as defined by [1] Definition 1.1
	 * @param t - Some arbitrary element of our time scale.
	 * @return The supremum of set of all items less than t in our time scale.
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public abstract double rho(double t) throws NotInTimeScaleException;
	
	/**
	 * @return T^Kappa as defined by [1] Definition 1.1
	 */
	public abstract TimeScale getTKappa();
	
	/**
	 * The forward graininess function as defined by [1] Definition 1.1
	 * @param t - Some arbitrary element of our time scale.
	 * @return The forward graininess of t.
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public double mu(double t) throws NotInTimeScaleException {
		return sigma(t) - t;
	}
	
	/**
	 * @return Whether or not t is right-scattered as defined by [1] Table 1.1 
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isRightScattered(double t) throws NotInTimeScaleException {
		return t < sigma(t);
	}
	
	/**
	 * @return Whether or not t is right-dense as defined by [1] Table 1.1
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isRightDense(double t) throws NotInTimeScaleException {
		return t == sigma(t);
	}
	
	/**
	 * @return Whether or not t is left-scattered as defined by [1] Table 1.1 
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isLeftScattered(double t) throws NotInTimeScaleException {
		return rho(t) < t;
	}
	
	/**
	 * @return Whether or not t is left-dense as defined by [1] Table 1.1
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isLeftDense(double t) throws NotInTimeScaleException {
		return rho(t) == t;
	}
	
	/**
	 * @return Whether or not t is scattered as defined by [1] Table 1.1
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isScattered(double t) throws NotInTimeScaleException {
		return isRightScattered(t) && isLeftScattered(t);
	}
	
	/**
	 * @return Whether or not t is dense as defined by [1] Table 1.1
	 * @throws NotInTimeScaleException - Given t is not in the time scale.
	 */
	public boolean isDense(double t) throws NotInTimeScaleException {
		return isRightDense(t) && isLeftDense(t);
	}
	
	/**
	 * Calculates the delta derivative of a given function at a point.
	 * 
	 * We assume f is differentiable at t
	 * @param t - Some arbitrary element of our time scale
	 * @return
	 */
	public double deltaDerivative(Function f, double t) throws NotInTimeScaleException {
		
		// By [1] Theorem 1.16 (ii)
		if (isRightScattered(t))
			return (f.evaluate(sigma(t)) - f.evaluate(t)) / mu(t);
		
		// Uses numerical methods to calculate the 1st order
		// derivative of f. Implementation is identical to that of
		// derivative in scipy.misc
		
		// TODO Implement numerical differentiation
		
		double value = 0;
		
		return value;
	}
}
