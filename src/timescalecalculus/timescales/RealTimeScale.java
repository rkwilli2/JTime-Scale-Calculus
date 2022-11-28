package timescalecalculus.timescales;

import mathutil.*;
import timescalecalculus.TimeScale;
import timescalecalculus.exceptions.*;

/**
 * 
 * An implementation of the time scale of the real numbers
 * 
 * @author Richard Williams
 * @since 11/25/2022
 */
public class RealTimeScale extends TimeScale {
	
	@Override
	public boolean isInTimeScale(double t) {
		return true;
	}
	
	@Override
	public Interval getIntervalFromValue(double t) throws NotInTimeScaleException {
		return new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}
	
	@Override
	public double sigma(double t) {
		return 0;
	}

	@Override
	public double rho(double t) {
		return 0;
	}

	@Override
	public TimeScale getTKappa() {
		// Sup{this} = INFINITY, so return this TimeScale
		return this;
	}
	
	@Override
	public double deltaIntegral(Function f, double lowerBound, double upperBound)
		throws NotInTimeScaleException {
		return integrateInterval(f, new Interval(lowerBound, upperBound));
	}

}
