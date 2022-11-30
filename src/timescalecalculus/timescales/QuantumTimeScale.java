package timescalecalculus.timescales;

import mathutil.*;
import timescalecalculus.TimeScale;
import timescalecalculus.exceptions.*;

/**
 * 
 * An implementation of the time scale of the Quantum Numbers. 
 * 
 * <br>
 * {0, ..., q^-2, q^-1, q^0, q, q^2, ...}
 * 
 * @author Richard Williams
 * @since 11/28/2022
 */
public class QuantumTimeScale extends TimeScale {

	private double q;
	
	private double MIN_Q;
	
	/**
	 * Creates the time scale of the quantum numbers.
	 * @param q - Some double greater than 1.
	 */
	public QuantumTimeScale(double q) {
		this.q = q;
		
		// Find the least non-zero element that can be represent in double precision
		MIN_Q = 1/q;
		try {
			// rho(MIN_Q) == 0 whenever there's a round-off error
			while(rho(MIN_Q) != 0) 
				MIN_Q = rho(MIN_Q);
		} catch (TimeScaleException e) {
			// This shouldn't ever happen since q^-1 is always in the time scale
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public boolean isInTimeScale(double t) {
		if (t == 0)
			return true;
		
		// Given t = q^a, return whether or not a is an integer
		return (Math.log(t) / Math.log(q)) % 1 == 0;
	}
	
	@Override
	public Interval getIntervalFromValue(double t) throws NotInTimeScaleException {
		if(isInTimeScale(t))
			return new Point(t);
		throw new NotInTimeScaleException(t);
	}

	@Override
	public double sigma(double t) throws NotInTimeScaleException {
		
		if(t == 0)
			return MIN_Q;
		
		if (isInTimeScale(t))
			return q * t;
		throw new NotInTimeScaleException(t);
	}

	@Override
	public double rho(double t) throws NotInTimeScaleException {
		if (isInTimeScale(t))
			return t / q;
		throw new NotInTimeScaleException(t);
	}

	@Override
	public TimeScale getTKappa() {
		// Sup{this} = INFINITY, so return this TimeScale
		return this;
	}

}
