package timescalecalculus;

import mathutil.*;
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
 * @since 11/28/2022
 */
public abstract class TimeScale {

	/**
	 * Machine Epsilon for double-precision arithmetic.
	 */
	public static final double EPSILON = 2.2E-16;
	
	/**
	 * Optimal step size for basic central differences to reduce error
	 */
	public static final double DIFF_STEP = Math.pow(EPSILON, 1/Math.pow(2, 16));
	
	/**
	 * @param t - Some arbitrary element of the reals
	 * @return whether or not t is an element of our time scale
	 */
	public abstract boolean isInTimeScale(double t);
	
	/**
	 * @param t - some arbitrary value of our time scale
	 * @return The interval that contains t that is in this time scale
	 * @throws NotInTimeScaleException - t is not in this time scale
	 */
	public abstract Interval getIntervalFromValue(double t) throws NotInTimeScaleException;
	
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
		
		// uses a finite difference approximation
		
		return (f.evaluate(t + DIFF_STEP) - f.evaluate(t - DIFF_STEP)) / (2 * DIFF_STEP);
	}
	
	/**
	 * Approximates the integral of a given function over some bounds
	 * <br>
	 * It is strongly recommended to override this whenever possible.
	 * @param f - some arbitrary function defined over the bounds given. We assume f is rd-continuous
	 * @param lowerBound - the lower bound of the integral. We assume lowerBound <= upperBound
	 * @param upperBound - the upper bound of the integral
	 * @return a numerical approximation of the integral of f over [lowerBound, upperBound]
	 * @throws NotInTimeScaleException - lowerBound and upperBound are not in this time scale
	 */
	public double deltaIntegral(Function f, double lowerBound, double upperBound)
		throws NotInTimeScaleException {
		
		// Check bounds
		if(!isInTimeScale(lowerBound))
			throw new NotInTimeScaleException(lowerBound);
		if(!isInTimeScale(upperBound))
			throw new NotInTimeScaleException(upperBound);
		
		if(upperBound < lowerBound)
			return -1 * deltaIntegral(f, upperBound, lowerBound);
		
		// If bounds are equal, return 0
		if(lowerBound == upperBound)
			return 0;
		
		// Begin numerical integration
		double sum = 0;
		double currentT = lowerBound;
		Interval currentInterval;
		
		while(currentT != upperBound) {
			// If mu(t) == 0, we're in an interval. Use integrateInterval
			if(mu(currentT) == 0) {
				currentInterval = getIntervalFromValue(currentT);
				
				// If upperBound is in this interval, add integral from currentT to upperBound
				if(currentInterval.contains(upperBound)) {
					sum += integrateInterval(f, currentInterval.setRightEndpoint(upperBound));
					currentT = upperBound;
				}
					
				else {
					sum += integrateInterval(f, currentInterval);
					currentT = currentInterval.getRightEndpoint();
				}
			}
		
			// Otherwise,
			else {
				// By [1] Theorem 1.75;
				sum += mu(currentT) * f.evaluate(currentT);
				currentT = sigma(currentT);
			}
		}
		
		return sum;
	}
	
	/**
	 * Integrates a function over a continuous interval using Composite Boole's Rule [3]
	 * <br>
	 * This should be used when overriding deltaIntegral for efficiency's sake
	 * for some subclass of TimeScale.
	 * @param f - some arbitrary function defined on the interval
	 * @param interval - the interval to integrate over
	 * @return a numerical approximation for the integral of f over interval
	 */
	protected double integrateInterval(Function f, Interval interval) {
		
		// Integral of any function from a to a is 0 :)
		if(interval.getLeftEndpoint() == interval.getRightEndpoint())
			return 0;
		
		double estimation;
		double stepSize;
		double distance = interval.getRightEndpoint() - interval.getLeftEndpoint();
		int numPoints = (int) distance;
		
		// Force bounds on numPoints for both error reducing & efficiency sake
		if(numPoints < 7500)
			numPoints = 7500;
		if(numPoints > 12500)
			numPoints = 12500;
		
		stepSize = distance / numPoints;
		
		// Begin Estimation
		
		estimation  = 7 * f.evaluate(interval.getLeftEndpoint());
		estimation += 7 * f.evaluate(interval.getRightEndpoint());
		
		for(int i = 1; i <= numPoints - 1; i += 2)
			estimation += 32 * f.evaluate(interval.getLeftEndpoint() + stepSize * i);
		
		for(int i = 2; i <= numPoints - 2; i += 4)
			estimation += 12 * f.evaluate(interval.getLeftEndpoint() + stepSize * i);
		
		for(int i = 4; i <= numPoints - 4; i += 4)
			estimation += 14 * f.evaluate(interval.getLeftEndpoint() + stepSize * i);
		
		estimation *= (2 * stepSize) / 45;
	
		return estimation;
	}

	
}
