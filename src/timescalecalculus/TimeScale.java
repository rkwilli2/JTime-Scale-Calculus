package timescalecalculus;

import timescalecalculus.exceptions.*;

/**
 * 
 * An arbitrary, nonempty closed subset of the real numbers.
 * <br>
 * Concrete methods are applicable to the arbitrary time scale whereas the 
 * abstract methods are to be defined by the user when the other time scale
 * implementations included in this library are not sufficient.
 * 
 * @author Richard Williams
 * @since 11/24/2022
 */
public abstract class TimeScale {

	/**
	 * Machine Epsilon for double-precision arithmetic.
	 */
	public static final double MACHINE_EPSILON = 2.2E-16;
	
	/**
	 * Optimal step size for basic central differences to reduce error [2]
	 */
	public static final double DIFF_STEP = Math.pow(MACHINE_EPSILON, 1/3.0);
	
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
	 * <br>
	 * If t is not right scattered in T^kappa, returns a numerical approximation
	 * made from the symmetric difference quotient. 
	 * <br>
	 * This approximation has an error of -DIFF_STEP^2 * f^(3)(t) / 6
	 * <br>
	 * We assume f is differentiable at t
	 * @param f - Some arbitrary function defined from T to R.
	 * @param t - Some arbitrary element of our time scale
	 * @return Delta derivative of f at t. 
	 */
	public double deltaDerivative(Function f, double t) throws NotInTimeScaleException {
		
		TimeScale tKappa = getTKappa();
		
		// By [1] Theorem 1.16 (ii)
		if (tKappa.isRightScattered(t))
			return (f.evaluate(tKappa.sigma(t)) - f.evaluate(t)) / tKappa.mu(t);
		
		// Symmetric difference quotient to approximate the derivative
		
		return (f.evaluate(t + DIFF_STEP) - f.evaluate(t - DIFF_STEP)) / (2 * DIFF_STEP);
	}
	
}
