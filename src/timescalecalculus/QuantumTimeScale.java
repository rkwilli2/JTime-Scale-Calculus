package timescalecalculus;

import timescalecalculus.exceptions.*;

/**
 * 
 * An implementation of the time scale of the Quantum Numbers. 
 * 
 * <br>
 * {0, ..., q^-2, q^-1, q^0, q, q^2, ...}
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class QuantumTimeScale extends TimeScale {

	private double q;
	
	/**
	 * Creates the time scale of the quantum numbers.
	 * @param q - Some double greater than 1.
	 */
	public QuantumTimeScale(double q) {
		this.q = q;
	}
	
	@Override
	public boolean isInTimeScale(double t) {
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
