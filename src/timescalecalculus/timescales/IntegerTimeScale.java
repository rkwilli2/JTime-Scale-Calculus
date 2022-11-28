package timescalecalculus.timescales;

import mathutil.*;
import timescalecalculus.TimeScale;
import timescalecalculus.exceptions.*;

/**
 * 
 * An implementation of the time scale of the multiple of the integers
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class IntegerTimeScale extends TimeScale {

	private double scalar;
	private double offset;
	
	/**
	 * Constructs the time scale of the integers
	 */
	public IntegerTimeScale() {
		scalar = 1;
		offset = 0;
	}
	
	/**
	 * Constructs the time scale of a multiple of the integers.
	 * <br>
	 * If the scalar is negative, it will be made positive with Math.abs
	 * @param scalar - the scalar for the set of integers
	 */
	public IntegerTimeScale(double scalar) {
		this.scalar = Math.abs(scalar);
		offset = 0;
	}

	/**
	 * Constructs the time scale of a multiple of the integers with some
	 * offset.
	 * <br>
	 * If the scalar is negative, it will be made positive with Math.abs
	 * <br>
	 * <pre>t = scalar * q + offset for some q in the integers for all t.</pre>
	 * 
	 * @param scalar - the scalar for the set of integers
	 * @param offset - the value of the above t when q=0.
	 */
	public IntegerTimeScale(double scalar, double offset) {
		this.scalar = Math.abs(scalar);
		this.offset = offset;
	}
	
	@Override
	public boolean isInTimeScale(double t) {
		return (t - offset) % scalar == 0;
	}
	
	@Override
	public Interval getIntervalFromValue(double t) throws NotInTimeScaleException {
		if(isInTimeScale(t))
			return new Point(t);
		throw new NotInTimeScaleException(t);
	}
	
	@Override
	public double sigma(double t) throws NotInTimeScaleException {
		if (isInTimeScale(t))
			return t + scalar;
		throw new NotInTimeScaleException(t);
	}

	@Override
	public double rho(double t) throws NotInTimeScaleException {
		if (isInTimeScale(t))
			return t - scalar;
		throw new NotInTimeScaleException(t);
	}

	@Override
	public TimeScale getTKappa() {
		// Sup{this} = INFINITY, so return this TimeScale
		return this;
	}
	
	@Override
	public double deltaIntegral(Function f, double lowerBound, double upperBound) 
		throws NotInTimeScaleException {
		
		if(!isInTimeScale(lowerBound))
			throw new NotInTimeScaleException(lowerBound);
		if(!isInTimeScale(upperBound))
			throw new NotInTimeScaleException(upperBound);
		
		double sum = 0;
		for(double i = lowerBound; i <= upperBound - 1; i += mu(i))
			sum += mu(i) * f.evaluate(i);
		
		return sum;
	}
	
}
